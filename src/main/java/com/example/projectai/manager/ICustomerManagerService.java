package com.example.projectai.manager;

import com.example.projectai.dto.CustomerDTO;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ICustomerManagerService {

  List<CustomerDTO> findAllCustomerDTO();

  CustomerDTO save(CustomerDTO customerDTO, String username);

  Integer delete(Map<String,List<String>> customerDTO);

  Optional<CustomerDTO> getCustomerByToken(String username);


}
