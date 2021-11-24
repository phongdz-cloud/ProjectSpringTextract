package com.example.projectai.service.Impl;

import com.example.projectai.entity.DailyMoneyEntity;
import com.example.projectai.repository.DailyMoneyRepository;
import com.example.projectai.service.IDailyMoneyService;
import java.util.List;
import java.util.Optional;
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
  public Boolean delete(String id) {
    DailyMoneyEntity dailyMoney = repository.findById(id).orElse(null);
    if(dailyMoney != null){
      repository.delete(dailyMoney);
      return true;
    }
    return false;
  }

  @Override
  public Optional<DailyMoneyEntity> findByCustomer(String id) {
    return repository.findByCustomer(id);
  }
}
