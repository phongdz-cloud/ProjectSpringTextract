package com.example.projectai.controller;

import com.example.projectai.dto.DailyMoneyDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.IDailyMoneyManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class DailyMoneyController {

  @Autowired
  private IDailyMoneyManagerService dailyMoneyManagerService;

  @Autowired
  private JwtProvider jwtProvider;

  @RequestMapping(value = "/daily", method = RequestMethod.GET)
  public ResponseEntity<List<DailyMoneyDTO>> findAllDailyMoney() {
    return ResponseEntity.ok(dailyMoneyManagerService.findAllDailyMoneyDTO());
  }

  @RequestMapping(value = "/dailyOfCustomer", method = RequestMethod.GET)
  public ResponseEntity<DailyMoneyDTO> finDailyByCustomer(HttpServletRequest request) {
    DailyMoneyDTO dailyMoneyDTO = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      dailyMoneyDTO = dailyMoneyManagerService.getDailyMoneyByToken(username);
      if (dailyMoneyDTO == null) {
        throw new RecordNotFoundException("Not find dailyMoney by customer: " + username);
      }
    }
    return ResponseEntity.ok(dailyMoneyDTO);
  }

  @RequestMapping(value = "/daily", method = RequestMethod.POST)
  public ResponseEntity<DailyMoneyDTO> createDaily(@Valid @RequestBody DailyMoneyDTO dailyMoneyDTO,
      HttpServletRequest request) {
    DailyMoneyDTO dailyMoney = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      dailyMoney = dailyMoneyManagerService.save(dailyMoneyDTO, username);
      if (dailyMoney == null) {
        throw new RecordNotFoundException("Not create or update dailymoney: " + username);
      }
    }
    return ResponseEntity.ok(dailyMoney);
  }

  @RequestMapping(value = "/daily", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteDailyMoney(@RequestBody Map<String, List<String>> ids,
      HttpServletRequest request) {
    int count = 0;
    if (jwtProvider.preHandle(request)) {
      count = dailyMoneyManagerService.delete(ids);
      if (count == 0) {
        throw new RecordNotFoundException("Not deleted element by element: " + count);
      }
    }
    return ResponseEntity.ok("Deleted by elements: " + count);
  }
}
