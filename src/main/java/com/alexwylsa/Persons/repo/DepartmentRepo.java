package com.alexwylsa.Persons.repo;

import com.alexwylsa.Persons.domain.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
    Page<Department> findAllByNameContainingIgnoreCase(String name, Pageable page);
    Long countByNameContains(String firstName);
}
