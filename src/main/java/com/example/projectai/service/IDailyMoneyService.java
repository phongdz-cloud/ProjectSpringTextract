package com.example.projectai.service;

import com.example.projectai.entity.DailyMoneyEntity;
import java.util.List;

public interface IDailyMoneyService {
  List<DailyMoneyEntity> findAllDailyMoney();

  DailyMoneyEntity save(DailyMoneyEntity dailyMoneyEntity);

  void delete(DailyMoneyEntity dailyMoneyEntity);
}
