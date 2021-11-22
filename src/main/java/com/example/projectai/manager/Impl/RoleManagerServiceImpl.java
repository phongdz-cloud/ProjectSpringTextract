package com.example.projectai.manager.Impl;

import com.example.projectai.dto.RoleDTO;
import com.example.projectai.entity.RoleEntity;
import com.example.projectai.manager.IRoleManagerService;
import com.example.projectai.service.IRoleService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class RoleManagerServiceImpl implements IRoleManagerService {

  private static final ModelMapper modelMapper = new ModelMapper();
  @Autowired
  private IRoleService roleService;

  @Override
  public RoleDTO save(RoleDTO role) {
    RoleEntity roleEntity = modelMapper.map(role, RoleEntity.class);
    return modelMapper.map(roleService.save(roleEntity), RoleDTO.class);
  }

  @Override
  public Optional<RoleDTO> findRoleDTOByName(String name) {
    return Optional.of(modelMapper.map(roleService.findRoleEntityByName(name), RoleDTO.class));
  }

  @Override
  public List<RoleDTO> findAllRoleDTO() {
    List<RoleEntity> roleEntities = roleService.findAllRole();
    return roleEntities
        .stream()
        .map(role -> modelMapper.map(role, RoleDTO.class))
        .collect(Collectors.toList());
  }

}
