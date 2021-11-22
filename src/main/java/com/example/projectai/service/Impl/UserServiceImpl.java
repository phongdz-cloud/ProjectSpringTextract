package com.example.projectai.service.Impl;

import com.example.projectai.entity.UserEntity;
import com.example.projectai.repository.UserRepository;
import com.example.projectai.service.IUserService;
import java.util.List;
import java.util.Optional;
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
    if (!existsByUsername(userEntity.getUsername()) && !existsByEmail(userEntity.getEmail())) {
      userEntity.setAvatar(
          "https://firebasestorage.googleapis.com/v0/b/hoaiphong-4cfd9.appspot.com/o/1637164284891-default_user.png?alt=media&token=63c398b0-3de2-4588-8304-720086d92e15");
      return userRepository.save(userEntity);
    }
    return null;
  }

  @Override
  public Optional<UserEntity> findByUsername(String username) {
    return userRepository.findByUsername(username);
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
