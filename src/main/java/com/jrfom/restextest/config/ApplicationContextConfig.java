package com.jrfom.restextest.config;

import java.util.List;

import com.jrfom.restextest.exception.FooException;
import com.jrfom.restextest.handler.FooExceptionHandler;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@Configuration
@EnableWebMvc
@ComponentScan (
  basePackages = (
    "com.jrfom"
  )
)
public class ApplicationContextConfig extends WebMvcConfigurerAdapter {
  private static final Logger log = LoggerFactory.getLogger(ApplicationContextConfig.class);

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    // Disable caching for a specific application path.
    /*WebContentInterceptor webContentInterceptor = new WebContentInterceptor();
    webContentInterceptor.setUseCacheControlHeader(true);
    webContentInterceptor.setCacheSeconds(0);
    webContentInterceptor.setUseCacheControlNoStore(true);
    webContentInterceptor.setUseCacheControlHeader(true);
    registry.addInterceptor(webContentInterceptor)
        .addPathPatterns("/secured/**");*/

    super.addInterceptors(registry);
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry
        .addResourceHandler("local/**")
        .addResourceLocations("/WEB-INF/assets/local/");

    // A jQuery example
//    registry
//        .addResourceHandler("jquery/**")
//        .addResourceLocations("/WEB-INF/assets/jquery-2.0.3/");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    super.addViewControllers(registry);
  }

  // Rest Exception Handler Config
  @Override
  public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
    resolvers.add(this.exceptionHandlerExceptionResolver());
    resolvers.add(this.restHandlerExceptionResolver());
    super.configureHandlerExceptionResolvers(resolvers);
  }

  @Bean
  public RestHandlerExceptionResolver restHandlerExceptionResolver() {
    return RestHandlerExceptionResolver.builder()
      .messageSource(this.httpErrorMessageSource())
      .defaultContentType(MediaType.APPLICATION_JSON)
      .addErrorMessageHandler(Exception.class, HttpStatus.INTERNAL_SERVER_ERROR)
      .addHandler(FooException.class, new FooExceptionHandler())
      .build();
  }

  @Bean
  public MessageSource httpErrorMessageSource() {
    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
    messageSource.setBasename("classpath:/restErrors");
    messageSource.setDefaultEncoding("UTF-8");

    return messageSource;
  }

  @Bean
  public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
    ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
    resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());

    return resolver;
  }
}