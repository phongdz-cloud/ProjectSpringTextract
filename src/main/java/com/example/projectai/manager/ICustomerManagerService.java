package com.example.projectai.manager;

import com.example.projectai.dto.CustomerDTO;
import java.util.List;
import java.util.Optional;

public interface ICustomerManagerService {

  List<CustomerDTO> findAllCustomerDTO();

  CustomerDTO save(CustomerDTO customerDTO, String username);

  void delete(CustomerDTO customerDTO);

  Optional<CustomerDTO> getCustomerByToken(String username);


}
