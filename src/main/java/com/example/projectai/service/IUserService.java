package com.example.projectai.service;

import com.example.projectai.entity.RoleEntity;
import com.example.projectai.entity.UserEntity;
import java.util.List;

public interface IUserService {

  List<UserEntity> findAllUser();

  UserEntity save(UserEntity userEntity);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);

}
