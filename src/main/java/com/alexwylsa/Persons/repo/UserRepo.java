package com.alexwylsa.Persons.repo;

import com.alexwylsa.Persons.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String user);
    Page<User> findAllByUsernameContains(String username, Pageable page);
}
