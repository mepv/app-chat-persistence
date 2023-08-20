package com.accenture.chatgpt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;
import java.util.Objects;

@Service
public class BaseService {

    private final ObjectMapper objectMapper;

    public BaseService() {
        this.objectMapper = JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    /**
     * Return a map from token string
     *
     * @param token
     * @return
     */
    public Map<String, String> getTokeMap(String token) {
        try {
            token = token.replace("Bearer ", "");
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();
            String payload = new String(decoder.decode(chunks[1]));
            return this.objectMapper.readValue(payload, Map.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("there was a error while getting the token");
        }
    }

    /**
     * Return name user from string token
     *
     * @param token
     * @return
     */
    public String getNameUserFromToken(String token) {
        Map<String, String> mapToken = this.getTokeMap(token);
        if (Objects.isNull(mapToken)) {
            return "S/N";
        }
        return mapToken.get("sub");
    }
}
