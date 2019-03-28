package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/staff")
@PreAuthorize("hasAuthority('ADMIN')")
public class StaffController {
    @Autowired
    private StaffService staffService;

    @GetMapping
    public List<Staff> getAllStaff(){
        return staffService.getAllStaff();
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

    @PutMapping("{id}/image")
    public Staff uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return staffService.uploadImage(id, file);
    }

    @GetMapping("{id}/image")
    public Staff getImage(@PathVariable Long id, @RequestParam("file") MultipartFile file){
        return staffService.getImage(id, file);
    }


}
