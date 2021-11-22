package com.example.projectai.service;

import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.UserEntity;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {

  List<CustomerEntity> findAllCustomer();

  CustomerEntity save(CustomerEntity customerEntity);

  void delete(CustomerEntity customerEntity);

  Optional<CustomerEntity> findCustomerByUserId(final String id);

}
