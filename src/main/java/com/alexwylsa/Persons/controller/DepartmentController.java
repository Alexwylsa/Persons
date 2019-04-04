package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/departments")
@PreAuthorize("hasAuthority('ADMIN')")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping ("{id}")
    public Department getDepartment(@PathVariable Long id ) {
        return departmentService.getDepartment(id);
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department){
        return  departmentService.addDepartment(department);
    }

    @PutMapping("{id}")
    public  Department updateDepartment(@PathVariable Long id, @RequestBody Department department){
        return departmentService.updateDepartment(department);
    }

    @DeleteMapping("{id}")
    public void deleteDepartment(@PathVariable Long id, Department department){
               departmentService.deleteDepartment(department);
    }
}
