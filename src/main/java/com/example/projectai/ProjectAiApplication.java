package com.example.projectai;

import io.github.kaiso.relmongo.config.EnableRelMongo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRelMongo
public class ProjectAiApplication {


  public static void main(String[] args) {
    SpringApplication.run(ProjectAiApplication.class, args);

  }

}
