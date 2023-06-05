package com.example.stock.facade;

import com.example.stock.repositoty.RedisLockRepository;
import com.example.stock.service.StockService;
import org.springframework.stereotype.Component;

@Component
public class LettuceLockStockFacade {

  private RedisLockRepository redisLockRepository;

  private StockService stockService;

  public LettuceLockStockFacade(RedisLockRepository redisLockRepository, StockService stockService) {
    this.redisLockRepository = redisLockRepository;
    this.stockService = stockService;
  }

  public void decrease(Long key, Long quantuty) throws InterruptedException {
    while(!redisLockRepository.lock(key)) {
      Thread.sleep(100);
    }
    try {
      stockService.decrease(key, quantuty);
    } finally {
      redisLockRepository.unlock(key);
    }
  }

}
