package com.example.projectai.dto;

import com.example.projectai.entity.PaymentEntity;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyMoneyDTO {

  private String id;

  private String nameMoney;

  private Float money;

  private LocalDateTime date;

  private String description;

  private CustomerDTO customer;

  private PaymentEntity payment;
}
