package com.example.projectai.service.Impl;

import com.example.projectai.entity.MonthlyMoneyEntity;
import com.example.projectai.repository.MonthlyMoneyRepository;
import com.example.projectai.service.IMonthlyMoneyService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MonthlyMoneyServiceImpl implements IMonthlyMoneyService {

  @Autowired
  MonthlyMoneyRepository repository;

  @Override
  public List<MonthlyMoneyEntity> findAllMonthlyMoney() {
    return repository.findAll();
  }

  @Override
  public MonthlyMoneyEntity save(MonthlyMoneyEntity monthlyMoneyEntity) {
    return repository.save(monthlyMoneyEntity);
  }

  @Override
  public Optional<MonthlyMoneyEntity> findByCustomer(String id) {
    return repository.findByCustomer(id);
  }

  @Override
  public Boolean delete(String id) {
    MonthlyMoneyEntity monthlyMoneyEntity = repository.findById(id).orElse(null);
    if (monthlyMoneyEntity != null) {
      repository.delete(monthlyMoneyEntity);
      return true;
    }
    return false;
  }
}
