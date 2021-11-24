package com.example.projectai.manager;

import com.example.projectai.dto.DailyMoneyDTO;
import java.util.List;
import java.util.Map;

public interface IDailyMoneyManagerService {

  List<DailyMoneyDTO> findAllDailyMoneyDTO();

  DailyMoneyDTO save(DailyMoneyDTO dailyMoneyDTO, String username);

  Integer delete(Map<String, List<String>> ids) ;
}
