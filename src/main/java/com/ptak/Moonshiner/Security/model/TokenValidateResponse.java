package com.ptak.Moonshiner.Security.model;

import com.ptak.Moonshiner.Security.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class TokenValidateResponse {

   private UserDTO user;
   private boolean isValid;

   public TokenValidateResponse(){}

   public UserDetails getUser(){
      return new User(user.getUsername(),user.getPassword(), new ArrayList<>());
   }
}
