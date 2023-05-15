//package com.accenture.chatgpt.apigateway.config;
//
//import java.util.Arrays;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.Ordered;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.codec.ServerCodecConfigurer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//@RefreshScope
//@Configuration
//@EnableResourceServer
//public class ResourceServerConfig2 extends ResourceServerConfigurerAdapter{
//
//	@Value("${config.security.oauth.jwt.key}")
//	private String jwtKey;
//
//	@Override
//	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//		resources.tokenStore(tokenStore());
//	}
//
//	@Override
//	public void configure(HttpSecurity http) throws Exception {
//		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
////		http.csrf().disable().authorizeRequests().antMatchers("/ms-auth/**").permitAll()
////		.antMatchers("/ms-chatgpt/**").hasAnyAuthority("ADMIN", "USER")
////		.anyRequest().authenticated()
////		.and().cors().configurationSource(corsConfigurationSource());
//	}
//
////	@Bean
////	public CorsConfigurationSource corsConfigurationSource() {
////		CorsConfiguration corsConfig = new CorsConfiguration();
////		corsConfig.setAllowedOrigins(Arrays.asList("*"));
////		corsConfig.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "OPTIONS"));
////		corsConfig.setAllowCredentials(true);
////		corsConfig.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
////
////		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
////		source.registerCorsConfiguration("/**", corsConfig);
////
////		return source;
////	}
//
////	@Bean
////	public FilterRegistrationBean<CorsFilter> corsFilter(){
////		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
////		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
////		return bean;
////	}
//
//	@Bean
//	public JwtTokenStore tokenStore() {
//		return new JwtTokenStore(accessTokenConverter());
//	}
//
//	@Bean
//	public JwtAccessTokenConverter accessTokenConverter() {
//		JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
//		tokenConverter.setSigningKey(jwtKey);
//		return tokenConverter;
//	}
//    @Bean
//    public ServerCodecConfigurer serverCodecConfigurer() {
//        return ServerCodecConfigurer.create();
//    }
//}
