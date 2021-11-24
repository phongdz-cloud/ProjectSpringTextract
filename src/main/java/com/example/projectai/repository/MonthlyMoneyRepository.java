package com.example.projectai.repository;

import com.example.projectai.entity.MonthlyMoneyEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyMoneyRepository extends MongoRepository<MonthlyMoneyEntity, String> {

  @Query("{'customer.id': ?0}")
  Optional<MonthlyMoneyEntity> findByCustomer(String id);
}
