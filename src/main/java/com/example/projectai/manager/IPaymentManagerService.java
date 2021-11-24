package com.example.projectai.manager;

import com.example.projectai.dto.PaymentDTO;
import com.example.projectai.entity.PaymentEntity;
import java.util.List;
import java.util.Map;
import org.springframework.web.multipart.MultipartFile;

public interface IPaymentManagerService {

  List<PaymentDTO> findAllPaymentDTO();

  PaymentDTO save(String username, MultipartFile file, String type);

  PaymentDTO saveHandPayment(String username, PaymentDTO paymentDTO);

  PaymentDTO update(PaymentDTO paymentDTO);

  Integer delete(Map<String, List<String>> id);

  List<PaymentEntity> findPaymentByCustomer(String username);

  List<PaymentDTO> findAllPaymentByCustomer(String username, String type);


}

