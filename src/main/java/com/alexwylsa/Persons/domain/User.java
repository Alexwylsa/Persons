package com.alexwylsa.Persons.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "usr")
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private boolean active;

//    private String email;
//    private String activationCode;

//    @CollectionTable(name = "department", joinColumns = @JoinColumn(name = "id"))
//    private String department_id;
//
//
//
//    @Column(updatable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
//    @JsonView
//    private LocalDateTime creationDate;
//
//    private String filename;

    @ElementCollection(targetClass = Role.class, fetch =  FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

//    public boolean isAdmin() {
//        return roles.contains(Role.ADMIN);
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive() ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();

    }


}
