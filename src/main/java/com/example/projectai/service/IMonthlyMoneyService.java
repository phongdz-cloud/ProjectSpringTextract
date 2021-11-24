package com.example.projectai.service;

import com.example.projectai.entity.MonthlyMoneyEntity;
import java.util.List;
import java.util.Optional;

public interface IMonthlyMoneyService {

  List<MonthlyMoneyEntity> findAllMonthlyMoney();

  MonthlyMoneyEntity save(MonthlyMoneyEntity monthlyMoneyEntity);

  Optional<MonthlyMoneyEntity> findByCustomer(String id);

  Boolean delete(String id);
}
