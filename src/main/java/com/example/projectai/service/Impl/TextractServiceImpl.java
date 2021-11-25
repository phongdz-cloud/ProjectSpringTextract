package com.example.projectai.service.Impl;

import com.example.projectai.dto.BoundingBox;
import com.example.projectai.dto.ItemLine;
import com.example.projectai.dto.SpecialField;
import com.example.projectai.dto.SummaryField;
import com.example.projectai.dto.TextractDTO;
import com.example.projectai.service.ITextractService;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.textract.TextractClient;
import software.amazon.awssdk.services.textract.model.AnalyzeExpenseRequest;
import software.amazon.awssdk.services.textract.model.AnalyzeExpenseResponse;
import software.amazon.awssdk.services.textract.model.Document;
import software.amazon.awssdk.services.textract.model.ExpenseDocument;
import software.amazon.awssdk.services.textract.model.ExpenseField;
import software.amazon.awssdk.services.textract.model.FeatureType;
import software.amazon.awssdk.services.textract.model.LineItemFields;
import software.amazon.awssdk.services.textract.model.LineItemGroup;

@Service
public class TextractServiceImpl implements ITextractService {

  public TextractDTO textractDTO = null;
  private AwsBasicCredentials awsCreds;
  private final Environment environment;

  public TextractServiceImpl(Environment environment) {
    this.environment = environment;
  }


  @PostConstruct
  private void initializeAWS() {
    awsCreds = AwsBasicCredentials.create(environment.getRequiredProperty("ACCESS_KEY_ID_AWS")
        , environment.getRequiredProperty("SECRET_ACCESS_KEY_AWS"));
  }

  public void initializeTextract(File file) {
    try {
      textractDTO = new TextractDTO();
      InputStream fis = null;
      fis = new FileInputStream(file);
      SdkBytes bytes = SdkBytes.fromInputStream(fis);
      Document doc = Document
          .builder()
          .bytes(bytes)
          .build();
      List<FeatureType> list = new ArrayList<>();
      list.add(FeatureType.FORMS);
      TextractClient textractClient = TextractClient
          .builder()
          .credentialsProvider(StaticCredentialsProvider.create(awsCreds))
          .region(Region.US_EAST_1)
          .build();
      AnalyzeExpenseRequest request = AnalyzeExpenseRequest
          .builder()
          .document(doc)
          .build();
      AnalyzeExpenseResponse response = textractClient.analyzeExpense(request);
      List<ExpenseDocument> test = response.expenseDocuments();
      for (ExpenseDocument exd : test) {
        DisplayAnalyzeExpenseSummaryInfo(exd);
        DisplayAnalyzeExpenseLineItemGroupsInfo(exd);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void DisplayAnalyzeExpenseSummaryInfo(ExpenseDocument expenseDocument) {
    if (expenseDocument.hasSummaryFields()) {
      List<ExpenseField> summaryfields = expenseDocument.summaryFields();
      for (ExpenseField summaryfield : summaryfields) {
        if (summaryfield.type() != null) {
          if (summaryfield.valueDetection() != null) {
            String type = summaryfield.type().text();
            BoundingBox boundingBox = new BoundingBox();
            boundingBox.setBox(summaryfield);
            String valueDetection = summaryfield.valueDetection().text();
            if (type.equals("OTHER")) {
              SummaryField other = new SummaryField();
              other.setFieldName(summaryfield.labelDetection().text());
              other.setValue(valueDetection);
              other.setBoundingBox(boundingBox);
              textractDTO.getSummaryFields().add(other);
            } else {
              SpecialField specialField = new SpecialField();
              specialField.setFieldName(type);
              if (type.equals("TOTAL") && valueDetection.contains(",")) {
                int index = valueDetection.indexOf(",");
                valueDetection =
                    valueDetection.substring(0, index) + "." + valueDetection.substring(index + 1);
              } else if (type.equals("TOTAL") && valueDetection.indexOf("$") == 0) {
                valueDetection = valueDetection.substring(1);
              }else if (type.equals("TOTAL") && valueDetection.indexOf("$") == valueDetection.length()){
                valueDetection = valueDetection.substring(0, valueDetection.length() - 1);
              }
              specialField.setValue(valueDetection);
              specialField.setBoundingBox(boundingBox);
              textractDTO.getSpecialFields().add(specialField);
            }
          }
        }
      }
    }
  }

  @Override
  public void DisplayAnalyzeExpenseLineItemGroupsInfo(ExpenseDocument expenseDocument) {
    if (expenseDocument.hasLineItemGroups()) {
      List<LineItemGroup> lineItemGroups = expenseDocument.lineItemGroups();
      for (LineItemGroup lineItemGroup : lineItemGroups) {
        if (lineItemGroup.hasLineItems()) {
          List<LineItemFields> lineItems = lineItemGroup.lineItems();
          for (LineItemFields lineItemFields : lineItems) {
            if (lineItemFields.hasLineItemExpenseFields()) {
              String name = lineItemFields.lineItemExpenseFields().get(0).valueDetection().text();
              String price = lineItemFields.lineItemExpenseFields().get(1).valueDetection().text();
              BoundingBox boundingBoxItem = new BoundingBox();
              BoundingBox boundingBoxPrice = new BoundingBox();
              if (name != null && price != null) {
                ItemLine itemLine = new ItemLine();
                itemLine.setItem(name);
                if (price.contains(",")) {
                  int index = price.indexOf(",");
                  price = price.substring(0, index) + "." + price.substring(index + 1);
                } else if (price.indexOf("$") == 0) {
                  price = price.substring(1);
                } else if (price.indexOf("$") == price.length()) {
                  price = price.substring(0, price.length() - 1);
                }
                itemLine.setPrice(Float.parseFloat(price));
                boundingBoxItem.setBox(lineItemFields.lineItemExpenseFields().get(0));
                boundingBoxPrice.setBox(lineItemFields.lineItemExpenseFields().get(1));
                itemLine.setBoundingBoxItem(boundingBoxItem);
                itemLine.setBoundingBoxPrice(boundingBoxPrice);
                textractDTO.getItemLines().add(itemLine);
              }
            }
          }
        }
      }
    }
  }
}
