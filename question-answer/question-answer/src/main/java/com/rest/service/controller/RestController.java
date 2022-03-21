package com.rest.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.rest.service.service.RestService;

@RequestMapping("/")
@org.springframework.web.bind.annotation.RestController
public class RestController {


  @Autowired
  private RestService service;

  @GetMapping("get/question")
  public ResponseEntity<String> getQuestion(@RequestParam("input") String input) {
    return service.question.apply(input);
  }

  @GetMapping("submit/answer")
  public ResponseEntity<String> submitAnswer(@RequestParam("answer") String answer) {
    return service.submitAnswer.apply(answer);
  }


}
