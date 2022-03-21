package com.rest.service.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rest.service.constants.ServiceConstants;
import com.rest.service.utility.SingletonMap;

@Service
public class RestService {

  @Autowired
  private SingletonMap singltonMap;


  private final Function<String, String> getOriginalQuestion =
      question -> question.substring(question.indexOf(ServiceConstants.QUESTION),
          question.indexOf(ServiceConstants.LAST_INDEX_SPLIT));

  private final Function<String, String> getAnswerQuestion =
      question -> question.substring(question.lastIndexOf(" ") + 1);

  private final Supplier<Integer> getRandomNumber = () -> 10 + new Random().nextInt(90);
  private final Supplier<String> getMessage = () -> {
    StringBuilder builder = new StringBuilder();
    Integer sum = 0;
    for (int counter = 0; counter < 3; counter++) {
      Integer number = getRandomNumber.get();
      builder.append(String.valueOf(number)).append(",");
      sum = sum + number;
    }
    builder.deleteCharAt(builder.length() - 1);
    String question = ServiceConstants.QUESTION.concat(builder.toString());
    Map<String, Integer> questionAnswer = new HashMap<>();
    questionAnswer.put(question, sum);
    singltonMap.setQuestionAnswer(questionAnswer);
    return ServiceConstants.MESSAGE.concat(builder.toString());
  };

  public Function<String, ResponseEntity<String>> question = question -> {
    if (StringUtils.equalsAnyIgnoreCase(ServiceConstants.GET_QUESTION, question)) {
      return new ResponseEntity<String>(getMessage.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Please enter correct input.", HttpStatus.NOT_ACCEPTABLE);
    }
  };

  public Function<String, ResponseEntity<String>> submitAnswer = answer -> {
    String question = getOriginalQuestion.apply(answer);
    Integer answerInt = Integer.valueOf(getAnswerQuestion.apply(answer));
    if (null != singltonMap.getQuestionAnswer()
        && singltonMap.getQuestionAnswer().containsKey(question)) {
      Integer value = singltonMap.getQuestionAnswer().get(question);
      if (answerInt.equals(value)) {
        return new ResponseEntity<String>(ServiceConstants.CORRECT_ANSWER, HttpStatus.OK);
      } else {
        return new ResponseEntity<String>(ServiceConstants.WRONG_ANSWER, HttpStatus.NOT_ACCEPTABLE);
      }
    } else {
      return new ResponseEntity<String>("Please submit correct question.",
          HttpStatus.NOT_ACCEPTABLE);
    }
  };


}
