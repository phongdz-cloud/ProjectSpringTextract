package com.example.projectai.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
  private String id;
  private String username;
  private String password;
  private String email;
  private String avatar;
  private RoleDTO role;
}
