package com.accenture.auth.config;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

	@Bean
	SecurityFilterChain configureSecurityFilterChain(HttpSecurity http) throws Exception {

		http
				.authorizeRequests(authorizeRequests -> authorizeRequests.anyRequest().authenticated())
				.formLogin(Customizer.withDefaults());

		return http.build();

	}


	@Bean
	public UserDetailsService users() {

		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

		UserDetails user = User.withUsername("admin")
				.password(encoder.encode("admin"))
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);

	}

}
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter{
//
//	@Autowired
//	private UserDetailsService userDetailsService;
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//	@Override
//	@Autowired
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.userDetailsService(this.userDetailsService)
//				.passwordEncoder(passwordEncoder());
//	}
//
//	@Override
//	@Bean
//	protected AuthenticationManager authenticationManager() throws Exception {
//		return super.authenticationManager();
//	}
//
//
//
//
//}
