package com.programmingdarinol.your_service_name;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan(basePackages = {"com.programmingdarinol.your_service_name.configuration.properties"})
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

}