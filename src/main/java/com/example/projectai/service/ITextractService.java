package com.example.projectai.service;

import com.example.projectai.entity.TextractEntity;
import software.amazon.awssdk.services.textract.model.ExpenseDocument;

public interface ITextractService {

  void DisplayAnalyzeExpenseSummaryInfo(ExpenseDocument expenseDocument, TextractEntity textract);

  void DisplayAnalyzeExpenseLineItemGroupsInfo(ExpenseDocument expenseDocument,
      TextractEntity textract);
}
