package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Department;
import com.alexwylsa.Persons.domain.DepartmentInDto;
import com.alexwylsa.Persons.exceptions.AlreadyExistsException;
import com.alexwylsa.Persons.exceptions.MyFileNotFoundException;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.DepartmentRepo;
import com.alexwylsa.Persons.parameters.SortParameters;
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
    private SortParameters sortParameters;
    //get all departments with parameters
    public List<Department> getAllDepartments(Optional<String> name, Integer page, Integer size, String byColumn,
                                              Integer ascending) {
        log.debug("getAllDepartments: name = {}, page = {}, size = {}, byColumn = {}, ascending = {}", name,
                page, size, byColumn, ascending);
        PageRequest pagination = sortParameters.parameters(page, size,
                byColumn, ascending);
        return name.map(u->departmentRepo.findAllByNameContainingIgnoreCase(u, pagination).getContent())
                .orElseGet(()->departmentRepo.findAll(pagination).getContent());
    }
    //count department
    public Long getDepartmentCount(Optional<String> name) {
        log.debug("getUsersCount: username = {}", name);
        return name.map(s -> departmentRepo.countByNameContains(s))
                .orElseGet(() -> departmentRepo.count());
    }
    //get one department
    public Department getDepartment(Long id) {
        log.debug("getDepartment: id = {} ", id);
        return  departmentRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }
    //add new department
    public Department addDepartment(DepartmentInDto departmentInData) {
        log.debug("getDepartment: departmentInData = {} ", departmentInData);
        if (departmentRepo.existsByName(departmentInData.getName())) {
            throw new AlreadyExistsException("Department already exist. Please choose another department name");
        }
        Department department = new Department();
        department.setName(departmentInData.getName());
        department.setBossId(departmentInData.getBossId());
        return departmentRepo.save(department);
    }
    //update department
    public Department updateDepartment(Long id, DepartmentInDto departmentInData) {
        log.debug("addDepartment: id = {}, departmentInData = {}", id, departmentInData);
        Department departmentFromDb = departmentRepo.findById(id).orElseThrow(()->new NotFoundException());
       departmentFromDb.setName(departmentInData.getName());
       departmentFromDb.setBossId(departmentInData.getBossId());
        return departmentRepo.save(departmentFromDb);
    }
    //delete department
    public void deleteDepartment(Long id, Department department){
        log.debug("deleteDepartment: id = {}, department = {}", id, department);
        departmentRepo.delete(department);
    }
}
