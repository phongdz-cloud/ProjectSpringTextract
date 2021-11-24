package com.example.projectai.service;

import software.amazon.awssdk.services.textract.model.ExpenseDocument;

public interface ITextractService {

  void DisplayAnalyzeExpenseSummaryInfo(ExpenseDocument expenseDocument);

  void DisplayAnalyzeExpenseLineItemGroupsInfo(ExpenseDocument expenseDocument);
}
