package com.example.projectai.manager;

import com.example.projectai.dto.DailyMoneyDTO;
import com.example.projectai.dto.MonthlyMoneyDTO;
import java.time.Month;
import java.util.List;
import java.util.Map;

public interface IMonthlyMoneyManagerService {

  List<MonthlyMoneyDTO> findAllMonthlyMoneyDTO();

  MonthlyMoneyDTO save(MonthlyMoneyDTO monthlyMoneyDTO,String username);

  Integer delete(Map<String,List<String>> ids);

  MonthlyMoneyDTO getMonthlyMoneyByToken(String username);
}
