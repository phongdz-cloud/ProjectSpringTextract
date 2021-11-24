package com.example.projectai.service;

import com.example.projectai.entity.PaymentEntity;
import java.util.List;
import java.util.Optional;

public interface IPaymentService {

  List<PaymentEntity> findAllPayment();

  PaymentEntity save(PaymentEntity paymentEntity);

  boolean delete(String id);

  List<PaymentEntity> findAllByCustomer(final String id);

  List<PaymentEntity> findAllByCustomerAndType(final String id, String type);

  Optional<PaymentEntity> findByCustomer(final String id);


}
