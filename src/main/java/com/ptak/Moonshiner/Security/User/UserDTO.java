package com.ptak.Moonshiner.Security.User;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class UserDTO {

    private String username;

    private String password;

    private boolean isEnabled;

    public UserDTO(){

    }

    public User toUser(){
        return new User(this.username, this.password, new ArrayList<>());
    }
}
