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
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Customer")
public class CustomerEntity {

  @Id
  private String id;
  @Field(value = "firstname")
  @NotNull(message = "Firstname must be not null")
  @Size(min = 2)
  private String firstName;
  @Field(value = "lastname")
  @Size(min = 2)
  @NotNull(message = "Lastname must be not null")
  private String lastName;
  @Field(value = "personal_income")
  @NotNull(message = "personal_income must be not null")
  private Float personalIncome;
  @Field(value = "monly_spending")
  @NotNull(message = "monthly_spending must be not null")
  private String monthlySpending;
  @Field(value = "user")
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinProperty(name = "user")
  private UserEntity user;
}
