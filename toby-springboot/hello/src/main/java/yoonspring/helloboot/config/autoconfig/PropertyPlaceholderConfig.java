package yoonspring.helloboot.config.autoconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import yoonspring.helloboot.config.MyAutoConfiguration;

@MyAutoConfiguration
public class PropertyPlaceholderConfig {

  @Bean
  PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

}
