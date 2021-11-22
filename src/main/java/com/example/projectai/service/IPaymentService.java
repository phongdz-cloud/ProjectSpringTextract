package com.example.projectai.service;

import com.example.projectai.entity.PaymentEntity;
import java.util.List;

public interface IPaymentService {
  List<PaymentEntity> findAllPayment();

  PaymentEntity save(PaymentEntity paymentEntity);

  void delete(PaymentEntity paymentEntity);
}