package com.example.projectai.controller;

import com.example.projectai.security.jwt.JwtProvider;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*")
public class TestController {

  @Autowired
  JwtProvider jwtProvider;

  @RequestMapping(value = "/test", method = RequestMethod.GET)
  public ResponseEntity<?> testJWT(HttpServletRequest request) {
    String username = null;
    if (jwtProvider.preHandle(request)) {
      username = jwtProvider.getUsernameFormToken(jwtProvider.getTokenWrapper());
    }
    return ResponseEntity.ok(username);
  }
}
