package com.example.projectai.repository;

import com.example.projectai.entity.PaymentEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentEntity, String> {

  @Query("{'customer.id': ?0}")
  List<PaymentEntity> findByCustomer(final String id);


}
