package com.example.projectai.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;

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
