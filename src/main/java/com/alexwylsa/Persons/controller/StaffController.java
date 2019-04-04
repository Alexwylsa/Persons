package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")

public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public List<Staff> getAllStaff(@RequestParam(required = false) Optional<String> lastName, @RequestParam Integer page, @RequestParam Integer size){
        return staffService.getAllStaff(lastName, page, size);
    }

    @GetMapping("{id}")
    public Staff getStaff(@PathVariable Long id){
        return staffService.getStaff(id);
    }

    @PostMapping
    public Staff addStaff(@RequestBody Staff staff){
        return staffService.addStaff(staff);
    }

    @PutMapping("{id}")
    public Staff updateStaff(@PathVariable Long id, @RequestBody Staff staff){
        return staffService.updateStaff(staff);
    }

    @DeleteMapping("{id}")
    public void deleteStaff(@PathVariable Long id, @RequestBody Staff staff){
        staffService.deleteStaff(staff);
    }
    @PostMapping("{id}")
    public void uploadPhoto(@PathVariable Long id, @RequestParam MultipartFile file) {
        staffService.addPhoto(id, file);
    }

}
