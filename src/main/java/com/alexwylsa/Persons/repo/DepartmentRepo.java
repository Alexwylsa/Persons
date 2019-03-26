package com.alexwylsa.Persons.repo;

import com.alexwylsa.Persons.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department, Long> {
}
