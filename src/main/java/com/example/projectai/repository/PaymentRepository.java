package com.example.projectai.repository;

import com.example.projectai.entity.PaymentEntity;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentEntity, String> {

  @Query("{'customer.id': ?0}")
  List<PaymentEntity> findAllByCustomer(final String id);

  @Query("{'customer.id': ?0, type: ?1}")
  List<PaymentEntity> findAllByCustomerAndType(final String id, String type);
}
