package com.example.projectai.manager;

import com.example.projectai.dto.CustomerDTO;
import com.example.projectai.dto.UserDTO;
import java.util.List;

public interface ICustomerManagerService {

  List<CustomerDTO> findAllCustomerDTO();

  CustomerDTO save(CustomerDTO customerDTO,String username);

  void delete(CustomerDTO customerDTO);

}
