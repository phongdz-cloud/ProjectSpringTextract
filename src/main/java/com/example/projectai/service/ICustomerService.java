package com.example.projectai.service;

import com.example.projectai.entity.CustomerEntity;
import java.util.List;

public interface ICustomerService {

  List<CustomerEntity> findAllCustomer();

  CustomerEntity save(CustomerEntity customerEntity);

  void delete(CustomerEntity customerEntity);
}
