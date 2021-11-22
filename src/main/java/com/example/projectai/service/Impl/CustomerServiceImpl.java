package com.example.projectai.service.Impl;

import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.repository.CustomerRepository;
import com.example.projectai.service.ICustomerService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements ICustomerService {

  @Autowired
  CustomerRepository repository;

  @Override
  public List<CustomerEntity> findAllCustomer() {
    return repository.findAll();
  }

  @Override
  public CustomerEntity save(CustomerEntity customerEntity) {
    return repository.save(customerEntity);
  }

  @Override
  public void delete(CustomerEntity customerEntity) {
    repository.delete(customerEntity);
  }
}
