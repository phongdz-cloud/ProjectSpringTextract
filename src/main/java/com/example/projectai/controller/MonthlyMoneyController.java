package com.example.projectai.controller;

import com.example.projectai.dto.MonthlyMoneyDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.IMonthlyMoneyManagerService;
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
public class MonthlyMoneyController {

  @Autowired
  private IMonthlyMoneyManagerService monthlyMoneyManagerService;

  @Autowired
  private JwtProvider jwtProvider;

  @RequestMapping(value = "/month", method = RequestMethod.GET)
  public ResponseEntity<List<MonthlyMoneyDTO>> findAllMonthMoney() {
    return ResponseEntity.ok(monthlyMoneyManagerService.findAllMonthlyMoneyDTO());
  }

  @RequestMapping(value = "/monthOfCustomer", method = RequestMethod.GET)
  public ResponseEntity<MonthlyMoneyDTO> findMonthlyByCustomer(HttpServletRequest request) {
    MonthlyMoneyDTO monthlyMoneyDTO = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      monthlyMoneyDTO = monthlyMoneyManagerService.getMonthlyMoneyByToken(username);
      if (monthlyMoneyDTO == null) {
        throw new RecordNotFoundException("Not find monthMoney by customer: " + username);
      }
    }
    return ResponseEntity.ok(monthlyMoneyDTO);
  }

  @RequestMapping(value = "/month", method = RequestMethod.POST)
  public ResponseEntity<MonthlyMoneyDTO> createMonthMoney(
      @Valid @RequestBody MonthlyMoneyDTO monthlyMoneyDTO, HttpServletRequest request) {
    MonthlyMoneyDTO moneyDTO = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      moneyDTO = monthlyMoneyManagerService.save(monthlyMoneyDTO, username);
      if (moneyDTO == null) {
        throw new RecordNotFoundException("Not create or update monthMoney: " + username);
      }
    }
    return ResponseEntity.ok(moneyDTO);
  }

  @RequestMapping(value = "/month", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteMonthMoney(@RequestBody Map<String, List<String>> ids,
      HttpServletRequest request) {
    int count = 0;
    if (jwtProvider.preHandle(request)) {
      count = monthlyMoneyManagerService.delete(ids);
      if (count == 0) {
        throw new RecordNotFoundException("Not deleted element by monthMoney: " + count);
      }
    }
    return ResponseEntity.ok("Deleted by elements: " + count);
  }
}
