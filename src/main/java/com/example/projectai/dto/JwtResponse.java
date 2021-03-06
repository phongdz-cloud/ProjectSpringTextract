package com.example.projectai.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

  private String token;
  private String type = "Bearer";
  private String name;
  private Collection<? extends GrantedAuthority> roles;
}
