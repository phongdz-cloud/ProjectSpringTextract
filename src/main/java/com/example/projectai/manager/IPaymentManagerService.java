package com.example.projectai.manager;

import com.example.projectai.dto.PaymentDTO;
import java.util.List;

public interface IPaymentManagerService {
  List<PaymentDTO> findAllPaymentDTO();

  PaymentDTO save(PaymentDTO paymentDTO);

  void delete(PaymentDTO paymentDTO);
}
