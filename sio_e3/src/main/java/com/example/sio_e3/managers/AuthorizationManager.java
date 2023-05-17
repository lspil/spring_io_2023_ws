package com.example.sio_e3.managers;

import org.springframework.stereotype.Component;

@Component
public class AuthorizationManager {

  public boolean rule(String username) {
    return true;
  }
}
