package com.geekapps.geeklibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.geekapps.geeklibrary", "port"})
public class GeekLibraryApplication {

  public static void main(final String[] args) {
    SpringApplication.run(GeekLibraryApplication.class, args);
  }

}
