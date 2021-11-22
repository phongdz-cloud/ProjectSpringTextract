package com.example.projectai.service;

import com.example.projectai.entity.MonthlyMoneyEntity;
import java.util.List;

public interface IMonthlyMoneyService {

  List<MonthlyMoneyEntity> findAllMonthlyMoney();

  MonthlyMoneyEntity save(MonthlyMoneyEntity monthlyMoneyEntity);

  void delete(MonthlyMoneyEntity monthlyMoneyEntity);
}
