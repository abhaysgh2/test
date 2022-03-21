package com.rest.service.utility;

import java.util.Map;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SingletonMap {

  private Map<String, Integer> questionAnswer;

  public Map<String, Integer> getQuestionAnswer() {
    return questionAnswer;
  }

  public void setQuestionAnswer(Map<String, Integer> questionAnswer) {
    this.questionAnswer = questionAnswer;
  }



}
