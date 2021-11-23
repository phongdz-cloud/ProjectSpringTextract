package com.example.projectai.service.Impl;

import com.example.projectai.dto.FileDTO;
import com.example.projectai.dto.FirebaseCredential;
import com.example.projectai.service.StorageStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageStrategy implements StorageStrategy {

  @Autowired
  TextractServiceImpl textractService;

  private final Logger log = LoggerFactory.getLogger(FirebaseStorageStrategy.class);

  private final Environment environment;

  private StorageOptions storageOptions;

  private String bucketName;

  private String projectId;

  private final StringBuilder firebaseURL = new StringBuilder("");

  public FirebaseStorageStrategy(Environment environment) {
    this.environment = environment;
  }

  @PostConstruct
  private void initializeFirebase() throws Exception {
    bucketName = environment.getRequiredProperty("FIREBASE_BUCKET_NAME");
    projectId = environment.getRequiredProperty("FIREBASE_PROJECT_ID");

    InputStream firebaseCredential = createFirebaseCredential();
    this.storageOptions = StorageOptions.newBuilder()
        .setCredentials(GoogleCredentials.fromStream(firebaseCredential))
        .build();
  }

  @Override
  public String[] uploadFile(MultipartFile multipartFile) throws Exception {
    firebaseURL.append("https://firebasestorage.googleapis.com/v0/b/").append(bucketName)
        .append("/o/");
    File file = convertMultiPartToFile(multipartFile);
    Path filePath = file.toPath();
    String objectName = generateFileName(multipartFile);
    Storage storage = storageOptions.getService();

    BlobId blobId = BlobId.of(bucketName, objectName);
    BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
        .setContentType(multipartFile.getContentType())
        .build();
    Blob blob = storage.create(blobInfo, Files.readAllBytes(filePath));
    firebaseURL.append(objectName).append("?alt=media");
    log.info(firebaseURL.toString());
    log.info("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    return new String[]{"fileUrl", objectName};
  }

  @Override
  public ResponseEntity<Object> downloadFile(String fileName, HttpServletRequest request)
      throws Exception {
    Storage storage = storageOptions.getService();
    Blob blob = storage.get(BlobId.of(bucketName, fileName));
    ReadChannel reader = blob.reader();
    InputStream inputStream = Channels.newInputStream(reader);
//    textractService.initializeTextract(inputStream);
    byte[] content = null;
    log.info("File downloaded successfully");
    return ResponseEntity.ok(textractService.textractEntity);
//    content = IOUtils.toByteArray(inputStream);
//    final ByteArrayResource byteArrayResource = new ByteArrayResource(content);
//    return ResponseEntity
//        .ok()
//        .contentLength(content.length)
//        .header("Content-type", "application/octet-stream")
//        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//        .body(byteArrayResource);
  }

  private String generateFileName(MultipartFile multiPart) {
    return new Date().getTime() + "-" + Objects.requireNonNull(
        multiPart.getOriginalFilename().replace(" ", "_"));
  }

  public File convertMultiPartToFile(MultipartFile file) throws Exception {
    File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    FileOutputStream fos = new FileOutputStream(convertedFile);
    fos.write(file.getBytes());
    fos.close();
    return convertedFile;
  }

  public FileDTO uploadFileDTO(MultipartFile file) throws Exception {
    String[] uploadedFile = uploadFile(file);
    String fileDownloadUri = firebaseURL.toString();
    String fileName = uploadedFile[1];
    log.info("fileDownloadUri, {0}" + fileDownloadUri);
    log.info("filename, {0}" + fileName);
    return new FileDTO(
        fileName,
        file.getContentType(),
        fileDownloadUri, file.getSize());

  }

  public ResponseEntity<Object> downloadFileEntity(String fileUrl, HttpServletRequest request) {
    try {
      return downloadFile(fileUrl, request);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }


  private InputStream createFirebaseCredential() throws Exception {
    //PRIVATE KEY

    FirebaseCredential firebaseCredential = new FirebaseCredential();
//private key
    String privateKey = environment.getRequiredProperty("FIREBASE_PRIVATE_KEY")
        .replace("\\n", "\n");
    firebaseCredential.setType(environment.getRequiredProperty("FIREBASE_TYPE"));
    firebaseCredential.setProject_id(projectId);
    firebaseCredential.setPrivate_key_id("FIREBASE_PRIVATE_KEY_ID");
    firebaseCredential.setPrivate_key(privateKey);
    firebaseCredential.setClient_email(environment.getRequiredProperty("FIREBASE_CLIENT_EMAIL"));
    firebaseCredential.setClient_id(environment.getRequiredProperty("FIREBASE_CLIENT_ID"));
    firebaseCredential.setAuth_uri(environment.getRequiredProperty("FIREBASE_AUTH_URI"));
    firebaseCredential.setToken_uri(environment.getRequiredProperty("FIREBASE_TOKEN_URI"));
    firebaseCredential.setAuth_provider_x509_cert_url(
        environment.getRequiredProperty("FIREBASE_AUTH_PROVIDER_X509_CERT_URL"));
    firebaseCredential.setClient_x509_cert_url(
        environment.getRequiredProperty("FIREBASE_CLIENT_X509_CERT_URL"));

    //serialize with jackson
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = mapper.writeValueAsString(firebaseCredential);
    //convert jsonString to InputStream using Apache Commons
    return IOUtils.toInputStream(jsonString);
  }
}
