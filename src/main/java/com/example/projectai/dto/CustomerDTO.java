package com.example.projectai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

  private String id;

  private String firstName;

  private String lastName;

  private Integer personalIncome;

  private String monthlySpending;

  private UserDTO user;
}
