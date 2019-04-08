package com.alexwylsa.Persons.repo;

import com.alexwylsa.Persons.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String user);
    Long countByUsernameContains(String username);
    Page<User> findAllByUsernameContainingIgnoreCase(String username, Pageable PageRequest);
}
