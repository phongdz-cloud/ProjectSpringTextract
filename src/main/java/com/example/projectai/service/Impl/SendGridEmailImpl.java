package com.example.projectai.service.Impl;

import com.example.projectai.service.ISendGridEmailService;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import java.io.IOException;
import javax.annotation.PostConstruct;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class SendGridEmailImpl implements ISendGridEmailService {

  private SendGrid sg;

  private Email from;

  private final Environment environment;

  public SendGridEmailImpl(Environment environment) {
    this.environment = environment;
  }

  @PostConstruct
  private void initializeSendGrid() {
    sg = new SendGrid(environment.getRequiredProperty("API_KEY"));
    from = new Email(environment.getRequiredProperty("EMAIL_FROM"));
  }

  @Override
  public void sendMail(String recepient, String template, String subject) {
    try {
      Email to = new Email(recepient);
      Content content = new Content("text/html",template);
      Mail mail = new Mail(this.from,subject,to,content);
      Request request = new Request();
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      Response response = sg.api(request);
      System.out.println(response.getStatusCode());
      System.out.println(response.getHeaders());
      System.out.println(response.getBody());
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}
