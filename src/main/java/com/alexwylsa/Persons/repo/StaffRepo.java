package com.alexwylsa.Persons.repo;

import com.alexwylsa.Persons.domain.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Long> {
    Page<Staff> findAllByLastNameContainingIgnoreCase(String lastName, Pageable page);
    Long countByFirstNameContains(String firstName);
}
