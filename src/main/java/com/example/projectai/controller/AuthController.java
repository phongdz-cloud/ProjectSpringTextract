package com.example.projectai.controller;

import com.example.projectai.dto.JwtResponse;
import com.example.projectai.dto.UserDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.IUserManagerService;
import com.example.projectai.security.jwt.JwtProvider;
import com.example.projectai.security.principle.UserPrinciple;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@CrossOrigin(origins = "*")
public class AuthController {

  @Autowired
  IUserManagerService userManagerService;
  @Autowired
  JwtProvider jwtProvider;
  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  PasswordEncoder passwordEncoder;

  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public ResponseEntity<UserDTO> userRegister(@Valid @RequestBody UserDTO userDTO) {
    userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
    UserDTO newUser = userManagerService.save(userDTO);
    if (newUser == null) {
      throw new RecordNotFoundException("Not create user!");
    }
    return ResponseEntity.ok(newUser);
  }

  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<JwtResponse> userLogin(@Valid @RequestBody UserDTO userDTO) {
    System.out.println("Code alive!");
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userDTO.getUsername(),
            userDTO.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.createToken(authentication);
    UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
    if (userPrinciple == null) {
      throw new RecordNotFoundException("Not user principle: " + userDTO.getUsername());
    }
    JwtResponse jwtResponse = new JwtResponse();
    jwtResponse.setToken(token);
    jwtResponse.setName(userPrinciple.getUsername());
    jwtResponse.setRoles(userPrinciple.getAuthorities());
    return ResponseEntity.ok(jwtResponse);
  }
}
