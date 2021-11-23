package com.example.projectai.manager;

import com.example.projectai.dto.PaymentDTO;
import com.example.projectai.entity.PaymentEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface IPaymentManagerService {

  List<PaymentDTO> findAllPaymentDTO();

  PaymentDTO save(String username, MultipartFile file);

  void delete(PaymentDTO paymentDTO);

  Optional<PaymentEntity> findPaymentByCustomer(String username);
}
