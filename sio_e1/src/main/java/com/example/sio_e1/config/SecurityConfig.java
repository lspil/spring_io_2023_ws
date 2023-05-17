package com.example.sio_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)
      throws Exception {

      http.httpBasic()
          .and()
        .formLogin()
          .and()
        .oauth2ResourceServer();

      http.authorizeHttpRequests()
        .requestMatchers("/demo").authenticated()
        .anyRequest().authenticated();

      http.csrf().disable();

//    http.authorizeRequests()
//        .mvcMatcher("/demo/**").authenticated();

    return http.build();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    UserDetails u1 = User.withUsername("bill")
        .password(passwordEncoder().encode("12345"))
        .authorities("read")
        .build();

    return new InMemoryUserDetailsManager(u1);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
