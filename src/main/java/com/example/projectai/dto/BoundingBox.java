package com.example.projectai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.textract.model.ExpenseField;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoundingBox {

  private Float width;
  private Float height;
  private Float left;
  private Float top;

  public void setBox(ExpenseField expenseField) {
    this.height = expenseField.valueDetection().geometry().boundingBox().height();
    this.width = expenseField.valueDetection().geometry().boundingBox().width();
    this.left = expenseField.valueDetection().geometry().boundingBox().left();
    this.top = expenseField.valueDetection().geometry().boundingBox().top();
  }
}
