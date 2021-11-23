package com.example.projectai.repository;

import com.example.projectai.entity.CustomerEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {

  @Query("{'user.id': ?0}")
  Optional<CustomerEntity> findByUser(final String id);


}

