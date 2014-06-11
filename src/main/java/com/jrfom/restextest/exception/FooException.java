package com.jrfom.restextest.exception;

import org.springframework.http.HttpStatus;

public class FooException extends Exception {
  private int code;

  public FooException(String message) {
    super(message != null ? message : "FooException was thrown for who knows why");
    this.code = HttpStatus.BAD_REQUEST.value();
  }

  public int getCode() {
    return this.code;
  }
}