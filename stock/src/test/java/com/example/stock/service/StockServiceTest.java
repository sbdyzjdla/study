package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repositoty.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StockServiceTest {

    @Autowired
    StockService stockService;

    @Autowired
    StockRepository stockRepository;

    @BeforeEach
    void before() {
        Stock stock = new Stock(1L, 100L);

        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void after() {
        stockRepository.deleteAll();
    }

    @Test
    void stock_decrease() {
        stockService.decrease(1L, 1L);

        Stock stock = stockRepository.findById(1L).orElseThrow();

        assertThat(stock.getQuantity()).isEqualTo(99L);
    }

    @Test
    void 동시에_100개의_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
               try {
                   stockService.decrease(1L, 1L);
               } finally {
                   latch.countDown();
               }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();

        // 100 - (1 * 100) = 0
        assertThat(stock.getQuantity()).isEqualTo(0L);

        /* 테스트가 실패한 이유
            스레드1이 재고가 100일때 접근, 스레드2도 재고가 100일때 접근
            스레드1이 재고를 하나줄임, 스레드2도 재고가 100일때 재고를 하나줄임

            ** Race Condition **
            두개 이상의 스레드가 공유 자원에 접근할수있고 동시에 변경하려고 할때 발생하는문제
            해결방법은 데이터에 한개의 스레드만 접근가능하게 해야함

            1. 자바에서의 해결방법 synchronized 사용
            그러다 @Transactional의 동작과정때문에 주석처리하고 사용해야함


         */

    }

}