package yoonspring.study;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.*;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

public class ConditionalTest {

  @Test
  void conditional() {
    /*
      TrueCondition으로 만든 bean은 만들어지고 FalseCondition으로 만든 bean은 만들어지지 않는다
      AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext();
      ac.register(Config1.class);
      ac.refresh();

      이렇게 직접만들어서 예외처리를 하며 테스트를 해도 되지만 테스트코드이므로 테스트용으로 만들어진 ApplicationContext를 이용해보자

     */

    // true
    ApplicationContextRunner contextRunner = new ApplicationContextRunner();
    contextRunner.withUserConfiguration(Config1.class)
        .run(context -> {
          assertThat(context).hasSingleBean(MyBean.class);
          assertThat(context).hasSingleBean(Config1.class);
        });

    //false
    new ApplicationContextRunner();
    contextRunner.withUserConfiguration(Config2.class)
        .run(context -> {
          assertThat(context).doesNotHaveBean(MyBean.class);
          assertThat(context).doesNotHaveBean(Config1.class);
        });


  }



  @Configuration
  @BooleanConditional(true)
  static class Config1 {
    @Bean
    MyBean myBean() {
      return new MyBean();
    }

  }

  @Configuration
  @BooleanConditional(false)
  static class Config2 {
    @Bean
    MyBean myBean() {
      return new MyBean();
    }
  }

  static class TrueCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return true;
    }
  }


  static class FalseCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      return false;
    }

  }

  static class MyBean {
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Conditional(TrueCondition.class)
  @interface TrueConditional {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Conditional(FalseCondition.class)
  @interface FalseConditional {}

  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.TYPE)
  @Conditional(BooleanCondition.class)
  @interface BooleanConditional {
    boolean value();
  }

  static class BooleanCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(BooleanConditional.class.getName());
      Boolean value = (Boolean) annotationAttributes.get("value");
      return value;
    }
  }

}
