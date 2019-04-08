package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.service.DepartmentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/departments")
@PreAuthorize("hasAuthority('ADMIN')")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments(@RequestParam(required = false) Optional<String> name,
                                              @RequestParam Integer page,
                                              @RequestParam Integer size){
       log.info("getAllDepartments: name = {}, page = {}, size = {}", name, page, size);
       return departmentService.getAllDepartments(name, page, size);
    }

    @GetMapping("/count")
    public Long getDepartmentCount(@RequestParam(required = false) Optional<String> name) {
        log.info("getUsersCount: username = {}", name);
        return departmentService.getDepartmentCount(name);
    }

    @GetMapping ("{id}")
    public Department getDepartment(@PathVariable Long id ) {
        log.info("getDepartment: id = {} ", id);
        return departmentService.getDepartment(id);
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department){
        log.info("addDepartment: department = {} ", department);
        return  departmentService.addDepartment(department);
    }

    @PutMapping("{id}")
    public  Department updateDepartment(@PathVariable Long id, @RequestBody Department department){
        log.info("addDepartment: id = {}, department = {}", id, department);
        return departmentService.updateDepartment(id, department);
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable Long id, Department department){
        log.info("deleteDepartment: id = {}, department = {}", id, department);
               departmentService.deleteDepartment(id, department);
    }
}
