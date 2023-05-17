package com.example.sio_e2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Value("${cors.origins}")
  private String corsOrigins;

  @Value("${cors.methods}")
  private String corsMethods;

  @Value("${cors.headers}")
  private String corsHeader;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.formLogin();

    http.csrf(c ->
        c.ignoringRequestMatchers("/demo"));

    http.cors(c -> c.configurationSource(
        request -> {
          CorsConfiguration configuration = new CorsConfiguration();
          configuration.setAllowedOrigins(List.of(corsOrigins));
          configuration.setAllowedHeaders(List.of(corsMethods));
          configuration.setAllowedMethods(List.of(corsHeader));
          return configuration;
        }
    ));

    return http.build();
  }
}
