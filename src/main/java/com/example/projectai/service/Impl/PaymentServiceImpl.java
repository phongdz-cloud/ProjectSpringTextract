package com.example.projectai.service.Impl;

import com.example.projectai.entity.PaymentEntity;
import com.example.projectai.repository.PaymentRepository;
import com.example.projectai.service.IPaymentService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements IPaymentService {

  @Autowired
  PaymentRepository repository;

  @Override
  public List<PaymentEntity> findAllPayment() {
    return repository.findAll();
  }

  @Override
  public PaymentEntity save(PaymentEntity paymentEntity) {
    return repository.save(paymentEntity);
  }


  @Override
  public boolean delete(String id) {
    PaymentEntity payment = repository.findById(id).orElse(null);
    if (payment != null) {
      repository.delete(payment);
      return true;
    }
    return false;
  }

  @Override
  public List<PaymentEntity> findAllByCustomer(String id) {
    return repository.findAllByCustomer(id);
  }

  @Override
  public List<PaymentEntity> findAllByCustomerAndType(String id, String type) {
    return repository.findAllByCustomerAndType(id, type);
  }

  @Override
  public Optional<PaymentEntity> findByCustomer(String id) {
    return repository.findByCustomer(id);
  }

}
