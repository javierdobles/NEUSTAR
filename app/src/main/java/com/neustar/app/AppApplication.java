package com.neustar.app;

import com.neustar.app.beans.ConfigurationBeans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

/** @author Javier Dobles */
@SpringBootApplication
public class AppApplication {

  public static void main(String[] args) {
    SpringApplication.run(AppApplication.class, args);
  }

  @Bean
  public AnnotationConfigApplicationContext context() {
    return new AnnotationConfigApplicationContext(ConfigurationBeans.class);
  }
}
