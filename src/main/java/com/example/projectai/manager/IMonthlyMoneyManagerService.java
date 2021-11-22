package com.example.projectai.manager;

import com.example.projectai.dto.MonthlyMoneyDTO;
import java.util.List;

public interface IMonthlyMoneyManagerService {

  List<MonthlyMoneyDTO> findAllMonthlyMoneyDTO();

  MonthlyMoneyDTO save(MonthlyMoneyDTO monthlyMoneyDTO);

  void delete(MonthlyMoneyDTO monthlyMoneyDTO);
}
