package com.example.projectai.service.Impl;

import com.example.projectai.entity.RoleEntity;
import com.example.projectai.repository.RoleRepository;
import com.example.projectai.service.IRoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  public RoleEntity save(RoleEntity role) {
    return roleRepository.save(role);
  }

  @Override
  public Optional<RoleEntity> findRoleEntityByName(String name) {
    return roleRepository.findByName(name);
  }

  @Override
  public List<RoleEntity> findAllRole() {
    return roleRepository.findAll();
  }

}
