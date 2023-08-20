package com.accenture.chatgpt.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * Security resource server.
 *
 * @author Rafa
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    private static final String SCOPE_ROLE_ADMIN = "SCOPE_ROLE_ADMIN";
    private static final String SCOPE_ROLE_USER = "SCOPE_ROLE_USER";
    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                .anonymous().disable()
                .authorizeExchange(authorize -> authorize
                                .pathMatchers("/v2/api-docs",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html",
                                        "/swagger-resources/**",
                                        "/configuration/ui",
                                        "/configuration/security",
                                        "/webjars/**").permitAll()
                                .pathMatchers("/oauth2/**").permitAll()
                                .pathMatchers( "/chatgpt/admin/**").hasAuthority(SCOPE_ROLE_ADMIN)
                                .pathMatchers( "/billing/**").hasAuthority(SCOPE_ROLE_ADMIN)
                                .pathMatchers(HttpMethod.GET, "/chatgpt/chat/**").hasAnyAuthority(SCOPE_ROLE_ADMIN, SCOPE_ROLE_USER)
                                .pathMatchers("/ms-auth/**").permitAll()
                                .pathMatchers(HttpMethod.GET, "/user/**").hasAuthority(SCOPE_ROLE_ADMIN)
                                .pathMatchers(HttpMethod.POST, "/user/username").permitAll()
                                .pathMatchers(HttpMethod.POST, "/user/admin/username").hasAuthority(SCOPE_ROLE_ADMIN)
                                .anyExchange().authenticated()
                )
                .oauth2ResourceServer().jwt();
        return http.build();
    }

}