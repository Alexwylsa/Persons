package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.DepartmentRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;

    public List<Department> getAllDepartments(Optional<String> name, Integer page, Integer size) {
        log.debug("getAllDepartments: name = {}, page = {}, size = {}", name, page, size);
        Pageable pagination = PageRequest.of(page, size);
        return name.map(d->departmentRepo.findAllByNameContainingIgnoreCase(d, pagination)).orElseGet(()->departmentRepo
                .findAll(pagination)).getContent();
    }

    public Long getDepartmentCount(Optional<String> name) {
        log.debug("getUsersCount: username = {}", name);
        return name.map(s -> departmentRepo.countByNameContains(s))
                .orElseGet(() -> departmentRepo.count());
    }

    public Department getDepartment(Long id) {
        log.debug("getDepartment: id = {} ", id);
        return  departmentRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public Department addDepartment(Department department) {
        log.debug("getDepartment: department = {} ", department);
        return departmentRepo.save(department);
    }

    public Department updateDepartment(Long id, Department department) {
        log.debug("addDepartment: id = {}, department = {}", id, department);
        departmentRepo.findById(id);
        department.setId(id);
        return departmentRepo.save(department);
    }

    public void deleteDepartment(Long id, Department department){
        log.debug("deleteDepartment: id = {}, department = {}", id, department);
        departmentRepo.delete(department);
    }
}
