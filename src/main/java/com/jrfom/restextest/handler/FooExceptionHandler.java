package com.jrfom.restextest.handler;

import javax.servlet.http.HttpServletRequest;

import com.jrfom.restextest.exception.FooException;
import cz.jirutka.spring.exhandler.handlers.RestExceptionHandler;
import cz.jirutka.spring.exhandler.messages.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class FooExceptionHandler implements RestExceptionHandler<FooException, ErrorMessage> {
  @Override
  public ResponseEntity<ErrorMessage> handleException(FooException exception, HttpServletRequest request) {
    HttpStatus status = HttpStatus.valueOf(exception.getCode());
    ErrorMessage errorMessage = new ErrorMessage();
    errorMessage.setTitle("Foo Exception");
    errorMessage.setStatus(status);
    errorMessage.setDetail(exception.getMessage());

    return new ResponseEntity<>(errorMessage, status);
  }
}