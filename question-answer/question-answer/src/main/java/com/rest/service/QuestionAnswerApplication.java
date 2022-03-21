package com.rest.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import com.rest.service.controller.RestController;

@ComponentScan(basePackages = {"com.rest.service.service", "com.rest.service.utility"},
    basePackageClasses = {RestController.class})
@SpringBootApplication
public class QuestionAnswerApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuestionAnswerApplication.class, args);
  }

}
