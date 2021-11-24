package com.example.projectai.manager.Impl;

import com.example.projectai.dto.CustomerDTO;
import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.manager.ICustomerManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import com.example.projectai.service.ICustomerService;
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
    }
    return Optional.ofNullable(customerDTO);
  }


}
