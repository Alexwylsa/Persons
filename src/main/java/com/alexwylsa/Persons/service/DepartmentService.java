package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.domain.DepartmentInDto;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.DepartmentRepo;
import com.alexwylsa.Persons.validators.DepartmentValidator;
import com.alexwylsa.Persons.validators.UserValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private UserValidator userValidator;

    public List<Department> getAllDepartments(Optional<String> name, Integer page, Integer size, String byColumn,
                                              Integer ascending) {
        log.debug("getAllDepartments: name = {}, page = {}, size = {}, byColumn = {}, ascending = {}", name,
                page, size, byColumn, ascending);
        PageRequest pagination = userValidator.validatePagingAndThrowAndReturn(page, size,
                byColumn, ascending);
        return name.map(u->departmentRepo.findAllByNameContainingIgnoreCase(u, pagination).getContent())
                .orElseGet(()->departmentRepo.findAll(pagination).getContent());
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

    public Department addDepartment(DepartmentInDto departmentInData) {
        log.debug("getDepartment: departmentInData = {} ", departmentInData);
        Department department = new Department();
        department.setName(departmentInData.getName());
        department.setBossId(departmentInData.getBossId());
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
