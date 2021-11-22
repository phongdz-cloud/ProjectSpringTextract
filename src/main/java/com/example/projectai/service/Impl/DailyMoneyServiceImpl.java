package com.example.projectai.service.Impl;

import com.example.projectai.entity.DailyMoneyEntity;
import com.example.projectai.repository.DailyMoneyRepository;
import com.example.projectai.service.IDailyMoneyService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DailyMoneyServiceImpl implements IDailyMoneyService {

  @Autowired
  DailyMoneyRepository repository;

  @Override
  public List<DailyMoneyEntity> findAllDailyMoney() {
    return repository.findAll();
  }

  @Override
  public DailyMoneyEntity save(DailyMoneyEntity dailyMoneyEntity) {
    return repository.save(dailyMoneyEntity);
  }

  @Override
  public void delete(DailyMoneyEntity dailyMoneyEntity) {
    repository.delete(dailyMoneyEntity);
  }
}
