package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.exceptions.ErrorCodes;
import com.alexwylsa.Persons.exceptions.RestException;
import com.alexwylsa.Persons.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class UserSecurityService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        log.debug("loadUserByUsername: user = {} ", user);
        return userRepo.findByUsername(user).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_FOUND));
    }
}
