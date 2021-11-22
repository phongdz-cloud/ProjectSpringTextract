package com.example.projectai.entity;

import com.example.projectai.dto.ItemLine;
import com.example.projectai.dto.SpecialField;
import com.example.projectai.dto.SummaryField;
import java.time.LocalDateTime;
import java.util.List;
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
@Document(collection = "payment")
public class PaymentEntity {

  @Id
  private String id;
  @Field(value = "image_bill")
  @NotNull(message = "image_bill must be not null")
  @Size(min=2)
  private String imageBill;
  @Field(value = "upload_date")
  private LocalDateTime uploadDate;
  @Field(value = "item_lines")
  private List<ItemLine> itemLines;
  @Field(value = "special_fields")
  private List<SpecialField> specialFields;
  @Field(value = "summary_fields")
  private List<SummaryField> summaryFields;
}
