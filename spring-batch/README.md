# 스프링 배치 내용정리

### 스프링 배치 활성화

* @EnableBatchProcessing : 스프링배치가 작동하기 위해 선언해야하는 어노테이션
  * Enable이 붙은 어노테이션들은 보통 특정한 기능이나 설정을 활성화하기 위해 사용되는 어노테이션

#### 스프링 배치 초기화 설정 클래스

1. BatchAutoConfiguration
2. SimpleBatchConfiguration
   * JobBuilderFactory, StepBuilderFactory 생성
   * 스프링 배치의 주요구성 요소 생성(프록시 객체로)
3. BatchConfigurerConfiguration
   * SimpleBatchConfiguration에서 생성한 프록시 객체의 실제 target객체를 생성하는 설정클래스
   * 빈으로 의존성 주입받아 객체들을 참조해서 사용함

순서 : @EnableBatchProcessing -> SimpleBatchConfiguration -> BatchConfigurerConfiguration -> BatchAutoConfiguration(JobLauncherApplicationRunner를 실행하기에 맨마지막)

#### 배치 동작과정

* Job구동 -> Job에 정의한 step실행 -> Tasklet실행 (Step 안에서 단일 태스크로 수행되는 로직 구현)
