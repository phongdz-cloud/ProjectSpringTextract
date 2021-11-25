package com.example.projectai.manager.Impl;

import com.example.projectai.dto.CustomerDTO;
import com.example.projectai.dto.SpecialField;
import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.DailyMoneyEntity;
import com.example.projectai.entity.MonthlyMoneyEntity;
import com.example.projectai.entity.PaymentEntity;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.manager.ICustomerManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import com.example.projectai.service.ICustomerService;
import com.example.projectai.service.IDailyMoneyService;
import com.example.projectai.service.IMonthlyMoneyService;
import com.example.projectai.service.IPaymentService;
import com.example.projectai.service.ISendGridEmailService;
import com.example.projectai.service.IUserService;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CustomerManagerServiceImpl implements ICustomerManagerService {

  private static final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  ICustomerService customerService;
  @Autowired
  IUserService userService;
  @Autowired
  JwtProvider jwtProvider;
  @Autowired
  IPaymentService paymentService;
  @Autowired
  IDailyMoneyService dailyMoneyService;
  @Autowired
  IMonthlyMoneyService monthlyMoneyService;
  @Autowired
  ISendGridEmailService sendGridEmailService;

  @Override
  public List<CustomerDTO> findAllCustomerDTO() {
    List<CustomerEntity> customerEntities = customerService.findAllCustomer();
    Type listType = new TypeToken<List<CustomerDTO>>() {
    }.getType();
    return modelMapper.map(customerEntities, listType);
  }

  @Override
  public CustomerDTO save(CustomerDTO customerDTO, String username) {
    UserEntity userEntity = userService.findByUsername(username).get();
    Optional<CustomerEntity> optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    CustomerEntity customerEntity = null;
    if (!optionalCustomer.isPresent()) {
      customerEntity = modelMapper.map(customerDTO, CustomerEntity.class);
      customerEntity.setUser(userEntity);
    } else {
      customerEntity = optionalCustomer.get();
      customerEntity.setFirstName(customerDTO.getFirstName());
      customerEntity.setLastName(customerDTO.getLastName());
      if (customerDTO.getPersonalIncome() != null) {
        customerEntity.setPersonalIncome(customerDTO.getPersonalIncome());
      }
      customerEntity.setMonthlySpending(customerDTO.getMonthlySpending());
    }
    return modelMapper.map(customerService.save(customerEntity), CustomerDTO.class);
  }

  @Override
  public Integer delete(Map<String, List<String>> ids) {
    int count = 0;
    for (String id : ids.get("ids")) {
      if (customerService.delete(id)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public Optional<CustomerDTO> getCustomerByToken(String username) {
    UserEntity userEntity = userService.findByUsername(username).get();
    Optional<CustomerEntity> optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    CustomerDTO customerDTO = null;
    if (optionalCustomer.isPresent()) {
      customerDTO = modelMapper.map(optionalCustomer.get(), CustomerDTO.class);
      List<PaymentEntity> paymentDailys = paymentService.findAllByCustomerAndType(
          optionalCustomer.get().getId(), "DAILY");
      List<PaymentEntity> paymentMonthlys = paymentService.findAllByCustomerAndType(
          optionalCustomer.get().getId(), "MONTHLY");
      float totalPriceDaily = getPricePaymentOfCustomer(paymentDailys);
      float totalPriceMonthly = getPricePaymentOfCustomer(paymentMonthlys);
      customerDTO.setTotalDaily(totalPriceDaily);
      customerDTO.setTotalMonthly(totalPriceMonthly);
      Optional<DailyMoneyEntity> dailyMoney = dailyMoneyService.findByCustomer(
          optionalCustomer.get().getId());
      Optional<MonthlyMoneyEntity> monthlyMoney = monthlyMoneyService.findByCustomer(
          optionalCustomer.get().getId());
      dailyMoney.ifPresent(
          dailyMoneyEntity -> alertPriceOfCustomer(totalPriceDaily, dailyMoneyEntity.getMoney(),
              userEntity.getEmail(), "DAILY"));
      monthlyMoney.ifPresent(
          monthlyMoneyEntity -> alertPriceOfCustomer(totalPriceMonthly,
              monthlyMoneyEntity.getMoney(),
              userEntity.getEmail(), "MONTHLY"));
    }
    return Optional.ofNullable(customerDTO);
  }

  private Float getPricePaymentOfCustomer(List<PaymentEntity> paymentEntities) {
    float total = 0F;
    for (PaymentEntity payment : paymentEntities) {
      for (SpecialField specialField : payment.getSpecialFields()) {
        if (specialField.getFieldName().equals("TOTAL")) {
          total += Float.parseFloat(specialField.getValue());
        }
      }
    }
    return total;
  }

  private void alertPriceOfCustomer(Float priceOfPayment, Float priceOfCustomer, String email,
      String type) {
    if (priceOfPayment > 0 && priceOfPayment > priceOfCustomer) {
      sendGridEmailService.sendMail(email, "Vượt quá tiền được cảnh báo " + type, "Alert");
    }
  }

}
