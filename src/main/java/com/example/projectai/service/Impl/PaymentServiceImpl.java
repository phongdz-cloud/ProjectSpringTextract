package com.example.projectai.service.Impl;

import com.example.projectai.entity.PaymentEntity;
import com.example.projectai.repository.PaymentRepository;
import com.example.projectai.service.IPaymentService;
import java.util.List;
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
  public void delete(PaymentEntity paymentEntity) {
      repository.delete(paymentEntity);
  }
}