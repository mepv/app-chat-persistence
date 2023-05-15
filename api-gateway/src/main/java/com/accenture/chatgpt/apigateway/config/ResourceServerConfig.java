//package com.accenture.chatgpt.apigateway.config;
//
////import com.javasm.cloud.gateway.utils.WebFluxUtils;
////import lombok.AllArgsConstructor;
////import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.security.authorization.ReactiveAuthorizationManager;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
//import org.springframework.security.web.server.authorization.AuthorizationContext;
//import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler;
//import reactor.core.publisher.Mono;
//
//
////@AllArgsConstructor
////@Slf4j
//@Configuration
//@EnableWebFluxSecurity
//public class ResourceServerConfig {
////    private final ReactiveAuthorizationManager<AuthorizationContext> authorizationManager=null;
////    private final IgnoreUrlUtils ignoreUrlUtils;
////    private final WhiteListAuthorizationFilter whiteListRemoveJwtFilter;
////    private final AuthGlobalFilter globalFilter;
//
//
//    @Bean
//    public SecurityWebFilterChain configure(ServerHttpSecurity http) throws Exception {
//        return http.authorizeExchange()
//                .pathMatchers("/ms-chatgpt/**").permitAll()
//                .anyExchange().authenticated()
//                .and().oauth2Login()
//                .and().build();
//    }
//
//}
//
