package com.example.projectai.service;

import com.example.projectai.entity.UserEntity;
import java.util.List;
import java.util.Optional;

public interface IUserService {

  List<UserEntity> findAllUser();

  UserEntity save(UserEntity userEntity);

  Optional<UserEntity> findByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);

}
