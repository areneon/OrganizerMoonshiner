package com.ptak.Moonshiner.Security.Service;

import com.ptak.Moonshiner.Security.User.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PersistanceService {

    @Autowired
    RestTemplate restTemplate;
    @Value("${persistance.application.name}")
    private String persistanceApplicationName;

    protected User getUserByUsernameFromDatabase(String username) {

        UserDTO userDTO = restTemplate.getForObject("http://localhost:8083/user/"+username, UserDTO.class);
        return userDTO.toUser();
    }
}
