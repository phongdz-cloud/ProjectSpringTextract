package com.example.projectai.service.Impl;

import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.repository.CustomerRepository;
import com.example.projectai.service.ICustomerService;
import java.util.List;
import java.util.Optional;
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
  public Boolean delete(String id) {
    CustomerEntity customerEntity = repository.findById(id).orElse(null);
    if (customerEntity != null) {
      repository.delete(customerEntity);
      return true;
    }
    return false;
  }

  @Override
  public Optional<CustomerEntity> findCustomerByUserId(String id) {
    return repository.findByUser(id);
  }

}
