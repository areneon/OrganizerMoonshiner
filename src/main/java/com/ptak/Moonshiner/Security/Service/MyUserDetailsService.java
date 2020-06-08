package com.ptak.Moonshiner.Security.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    PersistanceService persistanceService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return persistanceService.getUserByUsernameFromDatabase(username);
    }
}
