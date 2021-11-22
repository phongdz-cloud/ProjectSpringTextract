package com.example.projectai.manager;

import com.example.projectai.dto.CustomerDTO;
import java.util.List;

public interface ICustomerManagerService {

  List<CustomerDTO> findAllCustomerDTO();

  CustomerDTO save(CustomerDTO customerDTO);

  void delete(CustomerDTO customerDTO);
}
