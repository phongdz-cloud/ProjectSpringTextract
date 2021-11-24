package com.example.projectai.controller;

import com.example.projectai.dto.FileDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.service.Impl.FirebaseStorageStrategy;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class StorageController {

  private final Logger log = LoggerFactory.getLogger(StorageController.class);

  @Autowired
  private FirebaseStorageStrategy storageStrategy;

  @RequestMapping(value = "upload-file", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @Transactional
  public ResponseEntity<FileDTO> uploadInvestigation(@RequestParam("file") MultipartFile file)
      throws Exception {
    log.info("REST request to upload file");
    FileDTO fileDTO = storageStrategy.uploadFileDTO(file);
    if (fileDTO == null) {
      throw new RecordNotFoundException("Not update file procuess");
    }
    return new ResponseEntity<>(fileDTO, null, HttpStatus.OK);
  }

  @RequestMapping(value = "/download/{fileName:.+}", method = RequestMethod.GET)
  public ResponseEntity<Object> downloadFile(@PathVariable String fileName,
      HttpServletRequest request) {
    return storageStrategy.downloadFileEntity(fileName, request);
  }

}
