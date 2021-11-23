package com.example.projectai.manager.Impl;

import com.example.projectai.dto.FileDTO;
import com.example.projectai.dto.PaymentDTO;
import com.example.projectai.entity.CustomerEntity;
import com.example.projectai.entity.PaymentEntity;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.manager.IPaymentManagerService;
import com.example.projectai.service.ICustomerService;
import com.example.projectai.service.IPaymentService;
import com.example.projectai.service.IUserService;
import com.example.projectai.service.Impl.FirebaseStorageStrategy;
import com.example.projectai.service.Impl.TextractServiceImpl;
import com.google.common.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
@Service
public class PaymentManagerServiceImpl implements IPaymentManagerService {

  private static final ModelMapper modelMapper = new ModelMapper();

  @Autowired
  FirebaseStorageStrategy firebaseStorageStrategy;

  @Autowired
  IPaymentService paymentService;

  @Autowired
  ICustomerService customerService;

  @Autowired
  IUserService userService;

  @Autowired
  TextractServiceImpl textractService;

  private Optional<CustomerEntity> optionalCustomer = Optional.empty();

  @Override
  public List<PaymentDTO> findAllPaymentDTO() {
    List<PaymentEntity> paymentEntities = paymentService.findAllPayment();
    Type listType = new TypeToken<List<PaymentDTO>>() {
    }.getType();
    return modelMapper.map(paymentEntities, listType);
  }

  @Override
  public PaymentDTO save(String username, MultipartFile multiFile) {
    try {
      UserEntity userEntity = userService.findByUsername(username).get();
      optionalCustomer = customerService.findCustomerByUserId(
          userEntity.getId());
      if (optionalCustomer.isPresent()) {
        FileDTO fileDTO = firebaseStorageStrategy.uploadFileDTO(multiFile);
        if (fileDTO != null) {
          PaymentEntity paymentEntity = new PaymentEntity();
          paymentEntity.setUploadDate(LocalDateTime.now().toString());
          paymentEntity.setCustomer(optionalCustomer.get());
          paymentEntity.setImageBill(fileDTO.getFileDownloadUri());
          File file = firebaseStorageStrategy.convertMultiPartToFile(multiFile);
          textractService.initializeTextract(file);
          paymentEntity.setItemLines(textractService.textractEntity.getItemLines());
          paymentEntity.setSpecialFields(textractService.textractEntity.getSpecialFields());
          paymentEntity.setSummaryFields(textractService.textractEntity.getSummaryFields());
          return modelMapper.map(paymentService.save(paymentEntity), PaymentDTO.class);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public void delete(PaymentDTO paymentDTO) {
  }

  @Override
  public Optional<PaymentEntity> findPaymentByCustomer(String username) {
    Optional<PaymentEntity> optionalPayment = Optional.empty();
    UserEntity userEntity = userService.findByUsername(username).get();
    optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    if (optionalCustomer.isPresent()) {
      optionalPayment = paymentService.findByCustomer(
          optionalCustomer.get().getId());
    }
    return optionalPayment;
  }


}