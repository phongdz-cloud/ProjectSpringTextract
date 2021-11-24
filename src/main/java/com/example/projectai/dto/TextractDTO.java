package com.example.projectai.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TextractDTO {

  private List<ItemLine> itemLines = new ArrayList<>();
  private List<SummaryField> summaryFields = new ArrayList<>();
  private List<SpecialField> specialFields = new ArrayList<>();
}
