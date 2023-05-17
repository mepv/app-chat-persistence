package com.accenture.chatgpt.apigateway.config;

import com.netflix.discovery.CommonConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;

import javax.crypto.spec.SecretKeySpec;
import java.util.Arrays;

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
                .anonymous().disable()
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers("/oauth2/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/chatgpt/**").permitAll()
                        .pathMatchers("/ms-auth/**").permitAll()
                        .pathMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority("SCOPE_ROLE_ADMIN")
                        .anyExchange().authenticated()
                )
              //  .httpBasic().and()
                .oauth2ResourceServer().jwt();
//                .oauth2ResourceServer((resourceServer) -> resourceServer
//                        .jwt()
//                );
//                .oauth2ResourceServer((resourceServer) -> resourceServer
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(new CustomAuthenticationConverter("987654321"))
//                        )
//                );
        return http.build();
    }
//    @Bean
//    public AccessDecisionManager accessDecisionManager() {
//        java.util.List<AccessDecisionVoter<? extends Object>> decisionVoters
//                = Arrays.asList(
//                new WebExpressionVoter(),                        // You can add or remove the Role voters as per need
//                new RoleVoter(),                                 // For ROLE_ prefix
//                new AuthenticatedVoter(),
//                scopeVoterWithNoPrefix()                          // Get instance of ScopeVoter
//        );
//        return new UnanimousBased(decisionVoters);
//    }
//
//    @Bean
//    public ScopeVoter scopeVoterWithNoPrefix() {
//        ScopeVoter scopeVoter = new ScopeVoter();
//        scopeVoter.setScopePrefix("")
//        return scopeVoter;
//    }
//    @Bean
//    public Converter<Jwt, ? extends Mono<? extends AbstractAuthenticationToken>> jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//
//        // 把Jwt中的信息转化到SecurityContext#authentication#authorities中
//        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
//
//    @Bean
//    public ReactiveJwtDecoder jwtDecoder() {
//        return NimbusReactiveJwtDecoder.withSecretKey(new SecretKeySpec(
//                        CommonConstant.JWT_HMAC256_SECRET.getBytes(StandardCharsets.UTF_8), "HMAC256"))
//                .build();
//    }
}