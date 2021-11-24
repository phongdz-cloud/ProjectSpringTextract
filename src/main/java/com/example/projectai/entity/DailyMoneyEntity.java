package com.example.projectai.entity;

import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
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
@Document(collection = "daily_money")
public class DailyMoneyEntity {

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
  private String date;
  @Field(value = "description")
  @NotNull(message = "description must be not null")
  @Size(min = 2)
  private String description;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinProperty(name = "customer")
  private CustomerEntity customer;


}
