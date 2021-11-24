package com.example.projectai.service;

public interface ISendGridEmailService {

  void sendMail(String recipient, String template, String subject);
}
