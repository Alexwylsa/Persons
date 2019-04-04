package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public List<Department> getAllDepartments(Optional<String> name, Integer page, Integer size) {
        Pageable pagination = PageRequest.of(page, size);
        return name.map(q->departmentRepo.findAllByNameContains(q, pagination)).orElseGet(()->departmentRepo
                .findAll(pagination)).getContent();
    }

    public Department getDepartment(Long id) {
        return  departmentRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public Department addDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public Department updateDepartment(Department department) {
        return departmentRepo.save(department);
    }

    public void deleteDepartment(Department department){
        departmentRepo.delete(department);
    }
}
