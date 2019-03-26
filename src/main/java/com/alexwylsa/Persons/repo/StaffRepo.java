package com.alexwylsa.Persons.repo;

import com.alexwylsa.Persons.domain.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepo extends JpaRepository<Staff, Long> {
}
