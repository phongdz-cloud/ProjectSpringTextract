package com.example.projectai.controller;

import com.example.projectai.dto.PaymentDTO;
import com.example.projectai.manager.IPaymentManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
      HttpServletRequest request
      ) {
    PaymentDTO paymentDTO = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      paymentDTO = paymentManagerService.save(username, file);
      if (paymentDTO == null) {
        throw new RuntimeException("Not update bill customer: " + username);
      }
    }
    return ResponseEntity.ok(paymentDTO);
  }
}
