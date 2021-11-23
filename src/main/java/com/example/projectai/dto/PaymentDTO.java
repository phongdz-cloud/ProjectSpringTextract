package com.example.projectai.dto;

import com.example.projectai.entity.CustomerEntity;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

  private String id;

  private String imageBill;

  private String uploadDate;

  private List<ItemLine> itemLines;

  private List<SpecialField> specialFields;

  private List<SummaryField> summaryFields;

  private CustomerEntity customer;

}
