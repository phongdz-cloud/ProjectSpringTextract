package com.example.projectai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialField {

  private String fieldName;
  private String value;
  private BoundingBox boundingBox = new BoundingBox();
}
