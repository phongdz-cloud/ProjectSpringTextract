package com.example.projectai.controller;

import com.example.projectai.dto.UserDTO;
import com.example.projectai.exception.RecordNotFoundException;
import com.example.projectai.manager.IUserManagerService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class UserController {

  @Autowired
  private IUserManagerService userManagerService;

  @RequestMapping(value = "/user",method = RequestMethod.GET)
  public ResponseEntity<List<UserDTO>> findAllUser() {
    return ResponseEntity.ok(userManagerService.findAllUserDTO());
  }

  @RequestMapping(value = "/user",method = RequestMethod.POST)
  public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
    UserDTO user = userManagerService.save(userDTO);
    if (user == null) {
      throw new RecordNotFoundException("Create failed: " + user.getUsername());
    }

    return ResponseEntity.ok(user);
  }
}
