package com.example.projectai.repository;

import com.example.projectai.entity.UserEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

  Optional<UserEntity> findUserEntityByUsernameAndPassword(String username, String password);

  Optional<UserEntity> findByUsername(String username);

  Boolean existsByEmail(String email);

  Boolean existsByUsername(String username);

}
