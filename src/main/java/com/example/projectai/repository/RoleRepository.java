package com.example.projectai.repository;

import com.example.projectai.entity.RoleEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<RoleEntity, String> {

  Optional<RoleEntity> findByName(String name);

  Optional<RoleEntity> findRoleEntityById(String id);


}
