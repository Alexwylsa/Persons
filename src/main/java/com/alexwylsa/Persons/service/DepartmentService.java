package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.exeptions.NotFoundException;
import com.alexwylsa.Persons.repo.DepartmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepo departmentRepo;



    public List<Department> getAllDepartments() {

        return departmentRepo.findAll();

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
