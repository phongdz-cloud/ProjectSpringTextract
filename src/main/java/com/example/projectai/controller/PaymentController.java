package com.example.projectai.controller;

import com.example.projectai.dto.PaymentDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.IPaymentManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/api")
@RestController
@CrossOrigin("*")
public class PaymentController {

  @Autowired
  private IPaymentManagerService paymentManagerService;

  @Autowired
  private JwtProvider jwtProvider;

  @RequestMapping(value = "/payment", method = RequestMethod.GET)
  public ResponseEntity<List<PaymentDTO>> findAllPayment() {
    return ResponseEntity.ok(paymentManagerService.findAllPaymentDTO());
  }

  @RequestMapping(value = "/payment", method = RequestMethod.POST)
  public ResponseEntity<PaymentDTO> createPayment(@RequestParam("file") MultipartFile file,
      @RequestParam("type") String type,
      HttpServletRequest request
  ) {
    PaymentDTO paymentDTO = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      paymentDTO = paymentManagerService.save(username, file, type);
      if (paymentDTO == null) {
        throw new RuntimeException("Not update bill customer: " + username);
      }
    }
    return ResponseEntity.ok(paymentDTO);
  }

  @RequestMapping(value = "/handPayment", method = RequestMethod.POST)
  public ResponseEntity<PaymentDTO> createOrUpdatePayment(@Valid @RequestBody PaymentDTO paymentDTO,
      HttpServletRequest request) {
    PaymentDTO payment = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      if (username != null) {
        payment = paymentManagerService.saveHandPayment(username, paymentDTO);
        if (payment == null) {
          throw new RecordNotFoundException("Not create or update payment: " + username);
        }
      }
    }
    return ResponseEntity.ok(payment);
  }

  @RequestMapping(value = "/payment", method = RequestMethod.PUT)
  public ResponseEntity<PaymentDTO> updatePayment(HttpServletRequest request,
      @Valid @RequestBody PaymentDTO paymentDTO) {
    PaymentDTO payment = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      if (username != null) {
        payment = paymentManagerService.update(paymentDTO);
        if (payment == null) {
          throw new RecordNotFoundException("Not updated payment: " + username);
        }
      }
    }
    return ResponseEntity.ok(payment);
  }

  @RequestMapping(value = "/payment", method = RequestMethod.DELETE)
  public ResponseEntity<String> deletePayment(@RequestBody Map<String, List<String>> ids,
      HttpServletRequest request) {
    int count = 0;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      if (username != null) {
        count = paymentManagerService.delete(ids);
        if (count == 0) {
          throw new RecordNotFoundException("Not deleted element by ids: " + ids.toString());
        }
      }
    }
    return ResponseEntity.ok("Deleted by elements: " + count);
  }

  @RequestMapping(value = "/paymentOfCustomer", method = RequestMethod.POST)
  public ResponseEntity<List<PaymentDTO>> getPaymentOfCustomer(HttpServletRequest request,
      @RequestParam("type") String type) {
    List<PaymentDTO> paymentDTOS = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      if (username != null) {
        paymentDTOS = paymentManagerService.findAllPaymentByCustomer(username, type);
        if (paymentDTOS == null) {
          throw new RecordNotFoundException("Not payment of customer: " + username);
        }
      }
    }
    return ResponseEntity.ok(paymentDTOS);
  }

}
