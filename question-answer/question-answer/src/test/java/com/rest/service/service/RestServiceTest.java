package com.rest.service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import com.rest.service.constants.ServiceConstants;
import com.rest.service.utility.SingletonMap;

@ExtendWith(MockitoExtension.class)
public class RestServiceTest {

  @InjectMocks
  @Spy
  private RestService restService;

  @Mock
  private SingletonMap singltonMap;


  @AfterAll
  static void tearDownAfterClass() throws Exception {

  }

  @BeforeEach
  void setUp() throws Exception {
    ReflectionTestUtils.setField(restService, "singltonMap", singltonMap);
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {}


  @Test
  public void questionWithWrongInputTest() {
    ResponseEntity<String> response =
        restService.question.apply(ServiceConstants.GET_QUESTION + "abc");
    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());

  }

  @Test
  public void questionWithCorrectInputTest() {
    ResponseEntity<String> response = restService.question.apply(ServiceConstants.GET_QUESTION);
    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void submitAnswerWithCorrectValueTest() {
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put("Please sum the numbers 83,69,68", 220);
    singltonMap.setQuestionAnswer(questionAnswer);
    when(singltonMap.getQuestionAnswer()).thenReturn(questionAnswer);
    ResponseEntity<String> response = restService.submitAnswer.apply(
        "Great. The original question was “Please sum the numbers 83,69,68” and the answer is 220");
    assertEquals(HttpStatus.OK, response.getStatusCode());

  }

  @Test
  public void submitAnswerWithWrongValueTest() {
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put("Please sum the numbers 83,69,68", 220);
    singltonMap.setQuestionAnswer(questionAnswer);
    when(singltonMap.getQuestionAnswer()).thenReturn(questionAnswer);
    ResponseEntity<String> response = restService.submitAnswer.apply(
        "Great. The original question was “Please sum the numbers 83,69,68” and the answer is 120");
    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());

  }

  @Test
  public void submitAnswerWithWrongQuestionValueTest() {
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put("Please sum the numbers 83,69,68", 220);
    singltonMap.setQuestionAnswer(questionAnswer);
    when(singltonMap.getQuestionAnswer()).thenReturn(questionAnswer);
    ResponseEntity<String> response = restService.submitAnswer.apply(
        "Great. The original question was “Please sum the numbers 8,64,68” and the answer is 120");
    assertEquals(HttpStatus.NOT_ACCEPTABLE, response.getStatusCode());

  }

}
