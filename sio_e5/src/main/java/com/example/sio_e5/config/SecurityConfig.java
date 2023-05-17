package com.example.sio_e5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.oauth2ResourceServer(
        c -> c.jwt().jwkSetUri("http://localhost:8080/oauth2/jwks")
    );

//    http.oauth2ResourceServer(c ->
//        c.opaqueToken().introspectionUri("http://localhost:8080/oauth2/introspect")
//            .introspectionClientCredentials("client", "secret")
//    );

//    http.oauth2ResourceServer(
//        c -> c.authenticationManagerResolver()
//    );

    return http.build();
  }
}
