package com.example.projectai.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyMoneyDTO {

  private String id;

  private String nameMoney;

  private Float money;

  private String date;

  private String description;

//  private CustomerDTO customerDTO;

  private List<PaymentDTO> paymentDTO;
}
