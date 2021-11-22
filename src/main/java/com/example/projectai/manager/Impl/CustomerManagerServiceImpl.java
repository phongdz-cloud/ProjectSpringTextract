package com.example.projectai.manager.Impl;

import com.example.projectai.dto.CustomerDTO;
import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.manager.ICustomerManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import com.example.projectai.service.ICustomerService;
import com.example.projectai.service.IUserService;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
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
  public CustomerDTO save(CustomerDTO customerDTO) {

    return null;
  }

  @Override
  public void delete(CustomerDTO customerDTO) {

  }
}
