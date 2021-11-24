package com.example.projectai.service;

import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.DailyMoneyEntity;
import java.util.List;
import java.util.Optional;

public interface IDailyMoneyService {

  List<DailyMoneyEntity> findAllDailyMoney();

  DailyMoneyEntity save(DailyMoneyEntity dailyMoneyEntity);

  Boolean delete(String id);

  Optional<DailyMoneyEntity> findByCustomer(String id);

}
