package com.jrfom.restextest.controllers;

import com.jrfom.restextest.exception.FooException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
  private static final Logger log = LoggerFactory.getLogger(IndexController.class);

  @RequestMapping(value = "/")
  public String index() {
    log.info("Displaying index.html");
    return "index";
  }

  @RequestMapping(value = "/oops")
  public void oops() throws FooException {
    log.info("Displaying Foo exception for fun");
    throw new FooException("FooException was thrown by /oops");
  }
}