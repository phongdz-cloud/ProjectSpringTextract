package com.example.projectai.security.principle;

import com.example.projectai.entity.UserEntity;
import com.example.projectai.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserEntity userEntity = userRepository.findByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException("User not found -> username or password"));
    return UserPrinciple.build(userEntity);
  }
}
