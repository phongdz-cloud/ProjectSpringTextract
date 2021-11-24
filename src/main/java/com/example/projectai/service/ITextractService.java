package com.example.projectai.service;

import com.example.projectai.dto.TextractDTO;
import com.example.projectai.entity.TextractEntity;
import software.amazon.awssdk.services.textract.model.ExpenseDocument;

public interface ITextractService {

  void DisplayAnalyzeExpenseSummaryInfo(ExpenseDocument expenseDocument);

  void DisplayAnalyzeExpenseLineItemGroupsInfo(ExpenseDocument expenseDocument);
}
