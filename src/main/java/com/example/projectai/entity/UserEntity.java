package com.example.projectai.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.kaiso.relmongo.annotation.CascadeType;
import io.github.kaiso.relmongo.annotation.FetchType;
import io.github.kaiso.relmongo.annotation.JoinProperty;
import io.github.kaiso.relmongo.annotation.OneToOne;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "User")
public class UserEntity implements Serializable {

  @Id
  private String id;
  @Field(value = "username")
  @NotNull(message = "Username must be not null!")
  @Size(min = 6, max = 15)
  private String username;
  @Field(value = "password")
  @Size(min = 6)
  @JsonIgnore
  private String password;
  @Field(value = "email")
  @NotNull(message = "Email must be not null!")
  @Email
  private String email;
  private String avatar;
  @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinProperty(name = "role")
  private RoleEntity role;

}
