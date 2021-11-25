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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
  public PaymentDTO save(String username, MultipartFile multiFile, String type) {
    try {
      UserEntity userEntity = userService.findByUsername(username).get();
      optionalCustomer = customerService.findCustomerByUserId(
          userEntity.getId());
      if (optionalCustomer.isPresent()) {
        FileDTO fileDTO = firebaseStorageStrategy.uploadFileDTO(multiFile);
        if (fileDTO != null) {
          PaymentEntity paymentEntity = new PaymentEntity();
          paymentEntity.setType(type);
          paymentEntity.setUploadDate(LocalDateTime.now().toString());
          paymentEntity.setCustomer(optionalCustomer.get());
          paymentEntity.setImageBill(fileDTO.getFileDownloadUri());
          File file = firebaseStorageStrategy.convertMultiPartToFile(multiFile);
          textractService.initializeTextract(file);
          paymentEntity.setItemLines(textractService.textractDTO.getItemLines());
          paymentEntity.setSpecialFields(textractService.textractDTO.getSpecialFields());
          paymentEntity.setSummaryFields(textractService.textractDTO.getSummaryFields());
          return modelMapper.map(paymentService.save(paymentEntity), PaymentDTO.class);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  @Override
  public PaymentDTO saveHandPayment(String username, PaymentDTO paymentDTO) {
    PaymentEntity paymentEntity = null;
    try {
      UserEntity userEntity = userService.findByUsername(username).get();
      optionalCustomer = customerService.findCustomerByUserId(
          userEntity.getId());
      if (optionalCustomer.isPresent()) {
        paymentEntity = modelMapper.map(paymentDTO, PaymentEntity.class);
        Optional<PaymentEntity> optionalPaymentEntity = paymentService.findByCustomer(
            optionalCustomer.get().getId());
        if (!optionalPaymentEntity.isPresent()) {
          paymentEntity.setUploadDate(LocalDateTime.now().toString());
          paymentEntity.setCustomer(optionalCustomer.get());
        } else {
          paymentEntity = optionalPaymentEntity.get();
          paymentEntity.setUploadDate(LocalDateTime.now().toString());
          paymentEntity.setType(paymentDTO.getType());
          if (paymentDTO.getSummaryFields() != null) {
            paymentEntity.setSummaryFields(paymentDTO.getSummaryFields());
          }
          paymentEntity.setItemLines(paymentDTO.getItemLines());
          paymentEntity.setSpecialFields(paymentDTO.getSpecialFields());
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return modelMapper.map(paymentService.save(paymentEntity), PaymentDTO.class);
  }


  @Override
  public PaymentDTO update(PaymentDTO paymentDTO) {
    PaymentEntity paymentEntity = modelMapper.map(paymentDTO, PaymentEntity.class);
    paymentEntity.setUploadDate(LocalDateTime.now().toString());
    return modelMapper.map(paymentService.save(paymentEntity), PaymentDTO.class);
  }


  @Override
  public Integer delete(Map<String, List<String>> ids) {
    int count = 0;
    for (String id : ids.get("ids")) {
      if (paymentService.delete(id)) {
        count++;
      }
    }
    return count;
  }

  @Override
  public List<PaymentEntity> findPaymentByCustomer(String username) {
    List<PaymentEntity> paymentEntities = new ArrayList<>();
    UserEntity userEntity = userService.findByUsername(username).get();
    optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    if (optionalCustomer.isPresent()) {
      paymentEntities = paymentService.findAllByCustomer(
          optionalCustomer.get().getId());
    }
    return paymentEntities;
  }

  @Override
  public List<PaymentDTO> findAllPaymentByCustomer(String username, String type) {
    List<PaymentDTO> paymentDTOS = null;
    Type listType = new TypeToken<List<PaymentDTO>>() {
    }.getType();
    UserEntity userEntity = userService.findByUsername(username).get();
    optionalCustomer = customerService.findCustomerByUserId(
        userEntity.getId());
    if (optionalCustomer.isPresent()) {
      paymentDTOS = modelMapper.map(
          paymentService.findAllByCustomerAndType(optionalCustomer.get().getId(), type),
          listType);
    }
    return paymentDTOS;
  }
}
