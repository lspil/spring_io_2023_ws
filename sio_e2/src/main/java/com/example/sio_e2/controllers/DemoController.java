package com.example.sio_e2.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

//  @CrossOrigin("http://api.example.com")
  @PostMapping("/demo")
  public String demo() {
    return "Demo";
  }
}
