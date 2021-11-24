package com.example.projectai.service;

public interface ISendGridEmailService {

  void sendMail(String recepient, String template, String subject);
}
