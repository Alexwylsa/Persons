package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;



@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        return userRepo.findByUsername(user).orElseThrow(() -> new UsernameNotFoundException("Not found"));
    }
}
