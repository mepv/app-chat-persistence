package com.accenture.user.service;

import com.accenture.user.client.DataFeignClient;
import com.accenture.user.client.UserBillingFeignClient;
import com.accenture.user.dto.AdminUserDTO;
import com.accenture.user.dto.QuestionDateDTO;
import com.accenture.user.dto.RegistrationUserDTO;
import com.accenture.user.dto.UserDTO;
import com.accenture.user.dto.UserDataDTO;
import com.accenture.user.dto.UserDataDateDTO;
import com.accenture.user.model.Role;
import com.accenture.user.model.User;
import com.accenture.user.repository.RoleRepository;
import com.accenture.user.repository.UserRepository;
import feign.FeignException;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.accenture.user.model.Role.SCOPE_ROLE_ADMIN;
import static com.accenture.user.model.Role.SCOPE_ROLE_USER;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserBillingFeignClient userBillingFeignClient;
    private final DataFeignClient dataFeignClient;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder,
                       UserBillingFeignClient userBillingFeignClient,
                       DataFeignClient dataFeignClient,
                       ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userBillingFeignClient = userBillingFeignClient;
        this.dataFeignClient = dataFeignClient;
        this.modelMapper = modelMapper;
    }

    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException(String.format("error - username '%s' not found", username)));
    }

    @Transactional
    public String saveUserByUsername(RegistrationUserDTO userDTO) {
        boolean userExists = userRepository.findByUsername(userDTO.getUsername()).isPresent();
        if (userExists)
            throw new IllegalArgumentException(String.format("error - username '%s' already exists", userDTO.getUsername()));

        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        Role role;
        if (userDTO.getRole() == null) {
            Optional<Role> roleOptional = roleRepository.findByName(SCOPE_ROLE_USER);
            if (roleOptional.isPresent()) {
                role = roleOptional.get();
                user.setRole(role);
            }
        } else {
            role = roleRepository.findByName(userDTO.getRole().getName())
                    .orElseThrow(() -> new NoSuchElementException(String.format("role '%s' not found", userDTO.getRole().getName())));
            user.setRole(role);
        }

        userRepository.save(user);
        return String.format("User %s successfully created", user.getUsername());
    }

    @Transactional(readOnly = true)
    public List<UserDTO> usersByRole(String value) {
        String roleValue = "";
        if (value.equals("admin")) {
            roleValue = SCOPE_ROLE_ADMIN;
        } else if (value.equals("user")) {
            roleValue = SCOPE_ROLE_USER;
        }
        Role role = roleRepository.findByName(roleValue)
                .orElseThrow(() -> new NoSuchElementException(String.format("error - role '%s' not found", value)));
        List<UserDTO> users = userRepository.findAllByRole(role)
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
        if (users.isEmpty())
            throw new NoSuchElementException(String.format("no users found with the specified role '%s'", value));
        return users;
    }

    @Transactional(readOnly = true)
    public List<UserDTO> usersWithoutDebt() {
        List<String> usersWithoutDebt = userBillingFeignClient.getUsersWithoutDebt();
        return userRepository.findAllByUsernameIn(usersWithoutDebt)
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AdminUserDTO> adminsAskedOwnQuestions() {
        List<UserDataDTO> clientUsersData = dataFeignClient.getAdminsAskedOwnQuestions();
        List<UserDataDTO> clientUserBillings = userBillingFeignClient.getAdminsAskedOwnQuestions();

        Map<String, Set<String>> userDataMap = clientUsersData
                .stream()
                .collect(Collectors.toMap(
                        UserDataDTO::getUser,
                        userDataDTO -> new HashSet<>(userDataDTO.getQuestions())));

        Map<String, Set<String>> userBillingDataMap = clientUserBillings
                .stream()
                .collect(Collectors.toMap(
                        UserDataDTO::getUser,
                        userDataDTO -> new HashSet<>(userDataDTO.getQuestions())));

        Map<String, Set<String>> questionsAsked = new HashMap<>();
        Set<String> userDataMapQuestions;
        Set<String> userBillingDataMapQuestions;

        for (String keyUserDataMap : userDataMap.keySet()) {
            for (String keyUserBillingDataMap : userBillingDataMap.keySet()) {
                if (keyUserDataMap.equals(keyUserBillingDataMap)) {
                    userDataMapQuestions = userDataMap.get(keyUserDataMap);
                    userBillingDataMapQuestions = userBillingDataMap.get(keyUserBillingDataMap);
                    userDataMapQuestions.retainAll(userBillingDataMapQuestions);
                    questionsAsked.put(keyUserDataMap, userDataMapQuestions);
                }
            }
        }

        List<String> clientUsers = new ArrayList<>(userDataMap.keySet());
        List<User> users = userRepository.findAllByUsernameIn(clientUsers);
        Map<String, String> questionsMap = clientUsersData.get(0).getQuestionsMap();

        return users
                .stream()
                .map(user -> {
                    Set<String> set = questionsAsked.get(user.getUsername());
                    if (set == null)
                        throw new NoSuchElementException("there is no information, no admin has asked their own questions");
                    List<String> questions = new ArrayList<>(set)
                            .stream()
                            .map(questionsMap::get)
                            .collect(Collectors.toList());
                    return new AdminUserDTO(user.getFirstName(), user.getLastName(), user.getPhoneNumber(), questions);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UserDataDateDTO> getUserThatAskedQuestionsByDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("error - endDate must be after startDate");
        }

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        Map<String, String> questionsMap = dataFeignClient.getQuestionsByDate();
        List<UserDataDTO> clientBillingsData;
        try {
            clientBillingsData = userBillingFeignClient.getQuestionsByDate(startDate, endDate);
        } catch (FeignException e) {
            String start = startDate.format(dateFormatter);
            String end = endDate.format(dateFormatter);
            throw new NoSuchElementException(String.format("error - there is no information between dates '%s' and '%s'", start, end));
        }
        Map<String, Set<String>> userBillingDataMap = clientBillingsData
                .stream()
                .collect(Collectors.toMap(
                        UserDataDTO::getUser,
                        userDataDTO -> new HashSet<>(userDataDTO.getQuestions())));

        String uuid;
        LocalDateTime date;
        List<UserDataDateDTO> usersDate = new ArrayList<>();
        for (String user : userBillingDataMap.keySet()) {
            UserDataDateDTO userDataDateDTO = new UserDataDateDTO();
            List<QuestionDateDTO> questionsDateDTO = new ArrayList<>();
            userDataDateDTO.setUsername(user);
            for (String question : userBillingDataMap.get(user)) {
                QuestionDateDTO dto = new QuestionDateDTO();
                String[] questionsValues = question.split("/");
                uuid = questionsValues[0];
                date = LocalDateTime.parse(questionsValues[1]);

                dto.setQuestion(questionsMap.get(uuid));

                String formattedDate = date.format(dateFormatter);
                String formattedTime = date.format(timeFormatter);
                dto.setDate(formattedDate);
                dto.setTime(formattedTime);
                questionsDateDTO.add(dto);
            }
            userDataDateDTO.setData(questionsDateDTO);
            usersDate.add(userDataDateDTO);
        }
        return usersDate;
    }
}
