package com.example.sio_e3.services;

import com.example.sio_e3.entities.User;
import com.example.sio_e3.model.SecurityUser;
import com.example.sio_e3.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) {
    var user = userRepository.findUserByUsername(username);
    return user
        .map(u -> new SecurityUser(u))
        .orElseThrow(() -> new UsernameNotFoundException(":("));
  }

  @Transactional
  public void addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }
}
