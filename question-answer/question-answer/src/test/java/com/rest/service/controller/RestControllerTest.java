package com.rest.service.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.rest.service.constants.ServiceConstants;
import com.rest.service.service.RestService;
import com.rest.service.utility.SingletonMap;

@WebMvcTest(RestController.class)
public class RestControllerTest {


  @InjectMocks
  private RestService service;

  @Autowired
  public MockMvc mockMvc;

  @MockBean
  private SingletonMap singltonMap;

  @Test
  public void getQuestionWithWrongInputTest() throws Exception {
    mockMvc
        .perform(get("/get/question").param("input", ServiceConstants.GET_QUESTION + "abc")
            .accept(MediaType.APPLICATION_JSON))
        .andDo(print()).andExpect(status().isNotAcceptable());
  }

  @Test
  public void getQuestionWithCorrectInputTest() throws Exception {
    mockMvc.perform(get("/get/question").param("input", ServiceConstants.GET_QUESTION)
        .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void getSubmitAnswerTest() throws Exception {
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put("Please sum the numbers 83,69,68", 220);
    singltonMap.setQuestionAnswer(questionAnswer);
    when(singltonMap.getQuestionAnswer()).thenReturn(questionAnswer);
    mockMvc.perform(get("/submit/answer").param("answer",
        "Great. The original question was “Please sum the numbers 83,69,68” and the answer is 220")
        .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());

  }

  @Test
  public void getSubmitWithWrongAnswerTest() throws Exception {
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put("Please sum the numbers 83,69,68", 220);
    singltonMap.setQuestionAnswer(questionAnswer);
    when(singltonMap.getQuestionAnswer()).thenReturn(questionAnswer);
    mockMvc.perform(get("/submit/answer").param("answer",
        "Great. The original question was “Please sum the numbers 83,69,68” and the answer is 115")
        .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotAcceptable());

  }

  @Test
  public void getSubmitAnswerWithWrongQuestionTest() throws Exception {
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put("Please sum the numbers 83,69,68", 220);
    singltonMap.setQuestionAnswer(questionAnswer);
    when(singltonMap.getQuestionAnswer()).thenReturn(questionAnswer);
    mockMvc.perform(get("/submit/answer").param("answer",
        "Great. The original question was “Please sum the numbers 84,69,28” and the answer is 115")
        .accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isNotAcceptable());

  }

}
