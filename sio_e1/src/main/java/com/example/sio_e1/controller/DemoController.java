package com.example.sio_e1.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

  @GetMapping("/demo")
  public String demo() {
    return "Demo";
  }

  @GetMapping("/demo/hello")
  public String hello() {
    return "Demo";
  }
}
