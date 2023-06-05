package com.example.stock.service;

import com.example.stock.domain.Stock;
import com.example.stock.repositoty.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private StockRepository repository;

    public StockService(StockRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = repository.findById(id).orElseThrow();

        stock.decrease(quantity);

        repository.saveAndFlush(stock);
    }
}
