package com.example.sio_e3.controllers;

import com.example.sio_e3.entities.User;
import com.example.sio_e3.services.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class DemoController {

  private final CustomUserDetailsService userDetailsService;

  @GetMapping("/demo")
  public Authentication demo() {
    var a = SecurityContextHolder.getContext().getAuthentication();
    return a;
  }

  @PostMapping("/add")
  public void add(@RequestBody User user) {
    userDetailsService.addUser(user);
  }

  @GetMapping("/ciao")
  @PreAuthorize("authentication.name == #user.username")
  @PostAuthorize("authentication.authorities.contains(returnObject)")
  public String ciao(@RequestBody User user) {
    return "read";
  }

  @GetMapping("/hola")
  @PreAuthorize("@authorizationManager.rule(authentication.name)")
  @PreFilter("filterObject.contains('a')")
  @PostFilter("filterObject.contains('b')")
  public List<String> hola(@RequestBody List<String> input) {
    System.out.println(input);
    return input;
  }
}
