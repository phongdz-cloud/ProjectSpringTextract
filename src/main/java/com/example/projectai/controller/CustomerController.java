package com.example.projectai.controller;

import com.example.projectai.dto.CustomerDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.ICustomerManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import java.util.List;
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

//  @RequestMapping(value = "/customer/{username}", method = RequestMethod.GET)
//  public ResponseEntity<UserDTO> findCustomerByUser(@PathVariable String username) {
//    UserDTO userDTO = customerManagerService.findByUser(username);
//    if (userDTO == null) {
//      throw new RecordNotFoundException("Find customer by username failed: " + username);
//    }
//    return ResponseEntity.ok(userDTO);
//  }

}
