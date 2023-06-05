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

### 스프링 배치 메타 데이터

* 배치의 실행 및 관리를 위한 목적으로 정보들을 저장, 업데이트, 조회할수있는 스키마제공
* DB와 연동할 경우 필수로 메타 테이블이 생성되어야함

#### 스키마 생성 설정

* 수동 생성
  * 쿼리 복사후 직접 실행 /org/springframework/batch/core/schema.sql 에 있음
* 자동 생성
  * ALWAYS
    * 스크립트 항상 실행
    * RDBMS 설정시 내장 DB보다 우선실행
  * EMBEDDED
    * 기본값
  * NEVER
    * 스크립트 항상 실행 안함
    * 내장DB의 경우 스크립트가 생성이 안되기 때문에 오류발생
    * 운영에서 수동으로 스크립트 생성후 설정하는것을 권장


#### Job

* 배치 계층구조의 최상위 개념으로서 하나의 배치작업 자체를 의미
* 여러 step을 포함하는 컨테이너로서 하나이상의 step으로 구성
* 기본 구현체
  * SimpleJob
    * 순차적으로 스텝을 실행(표준 기능)
  * FlowJob
    * 특정 조건, 흐름에 따라 스텝을 실행
