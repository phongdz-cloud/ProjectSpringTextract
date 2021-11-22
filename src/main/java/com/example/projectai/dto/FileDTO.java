package com.example.projectai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDTO {

  private String fileName;
  private String contentType;
  private String fileDownloadUri;
  private Long fileSize;
}
