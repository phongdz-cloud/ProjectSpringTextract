package com.example.projectai.controller;

import com.example.projectai.dto.CustomerDTO;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.ICustomerManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import com.example.projectai.service.IPaymentService;
import com.example.projectai.service.IUserService;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CustomerController {

  @Autowired
  ICustomerManagerService customerManagerService;
  @Autowired
  JwtProvider jwtProvider;
  @Autowired
  IUserService userService;

  @RequestMapping(value = "/customer", method = RequestMethod.GET)
  public ResponseEntity<List<CustomerDTO>> findAllCustomer() {
    return ResponseEntity.ok(customerManagerService.findAllCustomerDTO());
  }

  @RequestMapping(value = "/customer", method = RequestMethod.POST)
  public ResponseEntity<CustomerDTO> createCustomer(HttpServletRequest request,
      @Valid @RequestBody CustomerDTO customerDTO) {
    CustomerDTO newCustomerDTO = null;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      newCustomerDTO = customerManagerService.save(customerDTO, username);
      if (newCustomerDTO == null) {
        throw new RecordNotFoundException("Create or Update failed: " + username);
      }
    }
    return ResponseEntity.ok(newCustomerDTO);
  }

  @RequestMapping(value = "/customerprofile", method = RequestMethod.GET)
  public ResponseEntity<CustomerDTO> getProfileCustomer(HttpServletRequest request) {
    Optional<CustomerDTO> optionalCustomerDTO = Optional.empty();
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      optionalCustomerDTO = customerManagerService.getCustomerByToken(
          username);
      if (!optionalCustomerDTO.isPresent()) {
        throw new RecordNotFoundException("Not found user profile Customer: " + username);
      }
    }
    return ResponseEntity.ok(optionalCustomerDTO.get());
  }

  @RequestMapping(value = "/customer", method = RequestMethod.DELETE)
  public ResponseEntity<String> deleteCustomer(@RequestBody Map<String, List<String>> ids,
      HttpServletRequest request) {
    int count = 0;
    if (jwtProvider.preHandle(request)) {
      String username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
      Optional<UserEntity> optionalUser = userService.findByUsername(username);
      if (optionalUser.isPresent() && optionalUser.get().getRole().getName().equals("ADMIN")) {
        count = customerManagerService.delete(ids);
        if (count == 0) {
          throw new RecordNotFoundException("Not deleted element by ids: " + ids.toString());
        }
      }
    }
    return ResponseEntity.ok("Deleted by elements: " + count);
  }

}
