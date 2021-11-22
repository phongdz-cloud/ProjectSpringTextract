package com.example.projectai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemLine {

  private String item;
  private Float price;
  private BoundingBox boundingBoxItem = new BoundingBox();
  private BoundingBox boundingBoxPrice = new BoundingBox();
}
