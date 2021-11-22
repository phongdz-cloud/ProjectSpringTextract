package com.example.projectai.repository;

import com.example.projectai.entity.MonthlyMoneyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyMoneyRepository extends MongoRepository<MonthlyMoneyEntity, String> {

}
