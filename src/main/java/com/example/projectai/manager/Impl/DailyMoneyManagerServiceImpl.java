package com.example.projectai.manager.Impl;

import com.example.projectai.dto.DailyMoneyDTO;
import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.DailyMoneyEntity;
import com.example.projectai.entity.PaymentEntity;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.manager.IDailyMoneyManagerService;
import com.example.projectai.service.ICustomerService;
import com.example.projectai.service.IDailyMoneyService;
import com.example.projectai.service.IPaymentService;
import com.example.projectai.service.IUserService;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DailyMoneyManagerServiceImpl implements IDailyMoneyManagerService {

  private static final ModelMapper modelMapper = new ModelMapper();

  @Autowired
  IUserService userService;

  @Autowired
  IPaymentService paymentService;

  @Autowired
  IDailyMoneyService dailyMoneyService;

  @Autowired
  ICustomerService customerService;


  @Override
  public List<DailyMoneyDTO> findAllDailyMoneyDTO() {
    List<DailyMoneyEntity> dailyMoneyEntities = dailyMoneyService.findAllDailyMoney();
    Type listType = new TypeToken<List<DailyMoneyDTO>>() {
    }.getType();
    return modelMapper.map(dailyMoneyEntities, listType);
  }

  @Override
  public DailyMoneyDTO save(DailyMoneyDTO dailyMoneyDTO, String username) {
    List<PaymentEntity> paymentEntities = findPaymentByCustomer(username);
    DailyMoneyEntity dailyMoneyEntity = modelMapper.map(dailyMoneyDTO, DailyMoneyEntity.class);
    dailyMoneyEntity.setPayments(paymentEntities);
    dailyMoneyEntity.setDate(LocalDateTime.now().toString());

    return null;
  }

  @Override
  public void delete(DailyMoneyDTO dailyMoneyDTO) {

  }

  public List<PaymentEntity> findPaymentByCustomer(String username) {
    List<PaymentEntity> paymentEntities = new ArrayList<>();
    UserEntity userEntity = userService.findByUsername(username).get();
    Optional<CustomerEntity> optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    if (optionalCustomer.isPresent()) {
      paymentEntities = paymentService.findByCustomer(
          optionalCustomer.get().getId());
    }
    return paymentEntities;
  }
}
