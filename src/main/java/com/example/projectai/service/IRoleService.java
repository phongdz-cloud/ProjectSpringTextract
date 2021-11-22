package com.example.projectai.service;

import com.example.projectai.entity.RoleEntity;
import java.util.List;
import java.util.Optional;

public interface IRoleService {

  RoleEntity save(RoleEntity role);

  Optional<RoleEntity> findRoleEntityByName(String name);

  List<RoleEntity> findAllRole();
}
