package com.recipe.book.config.oauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.recipe.book.domain.user.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
	
	private final CustomOAuth2UserService customOAuth2UserService;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        	.csrf().disable()
        	.headers().frameOptions().disable()
        	.and()
            .authorizeHttpRequests()
            .antMatchers("/", "/css/*", "/images/**",
            		"/js/**", "/h2-console/**").permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated()
            .and()
            	.logout()
            		.logoutSuccessUrl("/")
            .and()
            .oauth2Login()
            	.userInfoEndpoint()
            		.userService(customOAuth2UserService);
        return http.build();
    }
}
