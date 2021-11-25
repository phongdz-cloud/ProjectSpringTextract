package com.example.projectai.manager.Impl;

import com.example.projectai.dto.MonthlyMoneyDTO;
import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.MonthlyMoneyEntity;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.manager.IMonthlyMoneyManagerService;
import com.example.projectai.service.ICustomerService;
import com.example.projectai.service.IMonthlyMoneyService;
import com.example.projectai.service.IUserService;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class MonthlyManagerServiceImpl implements IMonthlyMoneyManagerService {

  private static final ModelMapper modelMapper = new ModelMapper();

  @Autowired
  IUserService userService;

  @Autowired
  IMonthlyMoneyService monthlyMoneyService;

  @Autowired
  ICustomerService customerService;

  @Override
  public List<MonthlyMoneyDTO> findAllMonthlyMoneyDTO() {
    List<MonthlyMoneyEntity> monthlyMoneyEntities = monthlyMoneyService.findAllMonthlyMoney();
    Type listType = new TypeToken<List<MonthlyMoneyDTO>>() {
    }.getType();
    return modelMapper.map(monthlyMoneyEntities, listType);
  }

  @Override
  public MonthlyMoneyDTO save(MonthlyMoneyDTO monthlyMoneyDTO, String username) {
    MonthlyMoneyEntity monthlyMoneyEntity = null;
    try {
      UserEntity userEntity = userService.findByUsername(username).get();
      Optional<CustomerEntity> optionalCustomer = customerService.findCustomerByUserId(
          userEntity.getId());
      if (optionalCustomer.isPresent()) {
        monthlyMoneyEntity = modelMapper.map(monthlyMoneyDTO, MonthlyMoneyEntity.class);
        Optional<MonthlyMoneyEntity> optionalMonthlyMoneyEntity = monthlyMoneyService.findByCustomer(
            optionalCustomer.get().getId());
        monthlyMoneyEntity.setDate(LocalDateTime.now().toString());
        if (!optionalMonthlyMoneyEntity.isPresent()) {
          monthlyMoneyEntity.setCustomer(optionalCustomer.get());
        } else {
          monthlyMoneyEntity = optionalMonthlyMoneyEntity.get();
          monthlyMoneyEntity.setNameMoney(monthlyMoneyDTO.getNameMoney());
          monthlyMoneyEntity.setDescription(monthlyMoneyDTO.getDescription());
          if (monthlyMoneyDTO.getMoney() != null) {
            monthlyMoneyEntity.setMoney(monthlyMoneyDTO.getMoney());
          }
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return modelMapper.map(monthlyMoneyService.save(monthlyMoneyEntity), MonthlyMoneyDTO.class);
  }

  @Override
  public Integer delete(Map<String, List<String>> ids) {
    Integer count = 0;
    for (String id : ids.get("ids")) {
      if (monthlyMoneyService.delete(id)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public MonthlyMoneyDTO getMonthlyMoneyByToken(String username) {
    UserEntity userEntity = userService.findByUsername(username).get();
    Optional<CustomerEntity> optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    MonthlyMoneyDTO monthlyMoneyDTO = null;
    if (optionalCustomer.isPresent()) {
      Optional<MonthlyMoneyEntity> optionalMonthlyMoneyEntity = monthlyMoneyService.findByCustomer(
          optionalCustomer.get().getId());
      if (optionalMonthlyMoneyEntity.isPresent()) {
        monthlyMoneyDTO = modelMapper.map(optionalMonthlyMoneyEntity.get(), MonthlyMoneyDTO.class);
      }
    }
    return monthlyMoneyDTO;
  }
}
