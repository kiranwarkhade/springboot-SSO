package com.kiran.springboot_sso_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@SpringBootApplication
//@EnableOAuth2Client
@Configuration
public class SpringBootSsoAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringBootSsoAppApplication.class, args);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests(auth ->
						auth.requestMatchers("/", "/public/**").permitAll()
						.anyRequest().authenticated())
				.oauth2Login(Customizer.withDefaults());
		return http.build();
	}
}
