package com.example.projectai.service;

import com.example.projectai.entity.CustomerEntity;
import java.util.List;
import java.util.Optional;

public interface ICustomerService {

  List<CustomerEntity> findAllCustomer();

  CustomerEntity save(CustomerEntity customerEntity);

  Boolean delete(String id);

  Optional<CustomerEntity> findCustomerByUserId(final String id);

}
