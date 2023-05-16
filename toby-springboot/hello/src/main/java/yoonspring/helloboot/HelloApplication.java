package yoonspring.helloboot;

import org.springframework.boot.SpringApplication;
import yoonspring.helloboot.config.MySpringBootApplication;

@MySpringBootApplication
public class HelloApplication {
  public static void main(String[] args) {
    SpringApplication.run(HelloApplication.class, args);
  }

}
