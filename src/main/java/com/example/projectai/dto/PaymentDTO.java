package com.example.projectai.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

  private String id;

  private String imageBill;

  private String uploadDate;

  private String type;

  private List<ItemLine> itemLines;

  private List<SpecialField> specialFields;

  private List<SummaryField> summaryFields;

  @JsonIgnore
  private CustomerDTO customer;

}
