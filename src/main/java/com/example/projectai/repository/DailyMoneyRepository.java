package com.example.projectai.repository;

import com.example.projectai.entity.DailyMoneyEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyMoneyRepository extends MongoRepository<DailyMoneyEntity,String> {

}
