package com.alexwylsa.Persons.domain;

import org.springframework.security.core.GrantedAuthority;
//user role enum class
public enum  Role implements GrantedAuthority {
    USER, ADMIN;
    @Override
    public String getAuthority() {
        return name();
    }
}
