package com.programmingdarinol.your_service_name.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

  @GetMapping
  public String hello(@RequestParam String name){
    return "Hello + " + name;
  }
}
