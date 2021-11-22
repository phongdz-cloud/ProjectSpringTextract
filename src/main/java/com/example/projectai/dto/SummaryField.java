package com.example.projectai.dto;

import java.util.HashMap;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummaryField {

  private String fieldName;
  private String value;
  private BoundingBox boundingBox = new BoundingBox();

}
