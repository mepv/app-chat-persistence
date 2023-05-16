package com.accenture.chatgpt.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security resource server.
 *
 * @author Rafa
 */
@Configuration
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.csrf().disable()
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/oauth2/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/chatgpt/**").permitAll()
                        .pathMatchers("/ms-auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority("ADMIN", "USER")
                        .anyExchange().authenticated()
                )
//                .oauth2ResourceServer((resourceServer) -> resourceServer
//                        .jwt(withDefaults())
//                );
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(jwt -> jwt
                                .jwtAuthenticationConverter(new CustomAuthenticationConverter("987654321"))
                        )
                );
        return http.build();
    }

}