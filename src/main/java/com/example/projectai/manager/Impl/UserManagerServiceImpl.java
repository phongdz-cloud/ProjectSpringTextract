package com.example.projectai.manager.Impl;

import com.example.projectai.Constant.SystemConstant;
import com.example.projectai.dto.UserDTO;
import com.example.projectai.entity.RoleEntity;
import com.example.projectai.entity.UserEntity;
import com.example.projectai.manager.IUserManagerService;
import com.example.projectai.service.IRoleService;
import com.example.projectai.service.IUserService;
import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class UserManagerServiceImpl implements IUserManagerService {

  private static final ModelMapper modelMapper = new ModelMapper();

  @Autowired
  private IUserService userService;
  @Autowired
  private IRoleService roleService;

  @Override
  public List<UserDTO> findAllUserDTO() {
    List<UserEntity> userEntities = userService.findAllUser();
    Type listType = new TypeToken<List<UserDTO>>() {
    }.getType();
    return modelMapper.map(userEntities, listType);
  }

  @Override
  public UserDTO save(UserDTO userDTO) {
    UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
    RoleEntity roleEntity = roleService.findRoleEntityByName(SystemConstant.USER).get();
    userEntity.setRole(roleEntity);
    return modelMapper.map(userService.save(userEntity), UserDTO.class);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return userService.existsByEmail(email);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return userService.existsByUsername(username);
  }
}
