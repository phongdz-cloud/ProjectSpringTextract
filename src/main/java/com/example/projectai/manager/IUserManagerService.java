package com.example.projectai.manager;

import com.example.projectai.dto.RoleDTO;
import com.example.projectai.dto.UserDTO;
import java.util.List;

public interface IUserManagerService {

  List<UserDTO> findAllUserDTO();

  UserDTO save(UserDTO userDTO);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);

}
