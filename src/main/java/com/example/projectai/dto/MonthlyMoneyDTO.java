package com.example.projectai.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

  private String date;

  private String description;

  @JsonIgnore
  private CustomerDTO customer;

}
