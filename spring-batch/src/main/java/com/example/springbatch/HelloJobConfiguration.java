package com.example.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class HelloJobConfiguration {

  private final JobBuilderFactory jobBuilderFactory;

  private final StepBuilderFactory stepBuilderFactory;

  @Bean
  public Job helloJob() {
    return jobBuilderFactory.get("helloJob")
        .start(helloStep1())
        .next(helloStep2())
        .build();
  }

  private Step helloStep1() {
    return stepBuilderFactory.get("helloStep1")
        .tasklet(new Tasklet() {
          @Override
          public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

            System.out.println(" ==============================");
            System.out.println(" >> Hello Spring Batch1");
            System.out.println(" ==============================");

            return RepeatStatus.FINISHED;
          }
        })
        .build();
  }

  private Step helloStep2() {
    return stepBuilderFactory.get("helloStep2")
        .tasklet(new Tasklet() {
          @Override
          public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

            System.out.println(" ==============================");
            System.out.println(" >> Hello Spring Batch2");
            System.out.println(" ==============================");

            return RepeatStatus.FINISHED;
          }
        })
        .build();
  }

}