package com.example.projectai.entity;

import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "monthly_money")
public class MonthlyMoneyEntity {

  @Id
  private String id;
  @Field(value = "name_money")
  @NotNull(message = "name_money must be not null")
  @Size(min = 2)
  private String nameMoney;
  @Field(value = "money")
  @NotNull(message = "money must be not null")
  private Float money;
  @Field(value = "date")
  private LocalDateTime date;
  @Field(value = "description")
  @NotNull(message = "description must be not null")
  @Size(min = 2)
  private String description;
  @Field(value = "customer")
  private CustomerEntity customer;
  @Field(value = "payment")
  private PaymentEntity payment;
}
