package com.example.projectai.service.Impl;

import com.example.projectai.entity.UserEntity;
import com.example.projectai.repository.UserRepository;
import com.example.projectai.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  private UserRepository userRepository;


  @Override
  public List<UserEntity> findAllUser() {
    return userRepository.findAll();
  }

  @Override
  public UserEntity save(UserEntity userEntity) {
    return userRepository.save(userEntity);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
