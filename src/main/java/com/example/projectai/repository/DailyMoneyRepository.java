package com.example.projectai.repository;

import com.example.projectai.entity.DailyMoneyEntity;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMoneyRepository extends MongoRepository<DailyMoneyEntity, String> {

  @Query("{'customer.id': ?0}")
  Optional<DailyMoneyEntity> findByCustomer(String id);

}
