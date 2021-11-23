package com.example.projectai.manager;

import com.example.projectai.dto.DailyMoneyDTO;
import java.util.List;

public interface IDailyMoneyManagerService {

  List<DailyMoneyDTO> findAllDailyMoneyDTO();

  DailyMoneyDTO save(DailyMoneyDTO dailyMoneyDTO, String username);

  void delete(DailyMoneyDTO dailyMoneyDTO);
}
