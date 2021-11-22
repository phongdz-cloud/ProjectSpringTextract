package com.example.projectai.entity;

import com.example.projectai.dto.ItemLine;
import com.example.projectai.dto.SpecialField;
import com.example.projectai.dto.SummaryField;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Textract")
public class TextractEntity {

  @Field(value = "itemline")
  private List<ItemLine> itemLines = new ArrayList<>();
  @Field(value = "summaryfiled")
  private List<SummaryField> summaryFields = new ArrayList<>();
  @Field(value = "specialfiled")
  private List<SpecialField> specialFields = new ArrayList<>();
}
