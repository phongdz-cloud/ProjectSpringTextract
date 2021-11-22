package com.example.projectai.manager;

import com.example.projectai.dto.RoleDTO;
import java.util.List;
import java.util.Optional;

public interface IRoleManagerService {

  RoleDTO save(RoleDTO role);

  Optional<RoleDTO> findRoleDTOByName(String name);

  List<RoleDTO> findAllRoleDTO();

}
