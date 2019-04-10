package com.alexwylsa.Persons.controller;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.domain.StaffInDto;
import com.alexwylsa.Persons.service.StaffService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/staff")

public class StaffController {
    @Autowired
    private StaffService staffService;
    //get all staff
    @GetMapping
    public List<Staff> getAllStaff(@RequestParam(required = false) Optional<String> lastName,
                                   @RequestParam Integer page,
                                   @RequestParam Integer size,
                                   @RequestParam(value = "orderBy", required = false) String byColumn,
                                   @RequestParam Integer ascending){
        log.info("getAllStaff: lastName = {}, page = {}, size = {}, byColumn = {}, ascending = {}", lastName,
                page, size, byColumn, ascending);
        return staffService.getAllStaff(lastName, page, size, byColumn, ascending);
    }
    //count staff in DB
    @GetMapping("/count")
    public Long getStaffCount(@RequestParam(required = false) Optional<String> firstName) {
        log.info("getUsersCount: username = {}", firstName);
        return staffService.getStaffCount(firstName);
    }
    //get one staff
    @GetMapping("{id}")
    public Staff getStaff(@PathVariable Long id){
        log.info("getStaff: id = {} ", id);
        return staffService.getStaff(id);
    }
    //add new staff
    @PostMapping("/")
    public Staff addStaff(@RequestBody StaffInDto staff){
        log.info("addStaff: staff = {} ", staff);
        return staffService.addStaff(staff);
    }
    //update staff
    @PutMapping("{id}")
    public Staff updateStaff(@PathVariable Long id, @RequestBody StaffInDto staff){
        log.info("updateStaff: id = {}, staff = {}", id, staff);
        return staffService.updateStaff(id, staff);
    }
    //delete staff
    @DeleteMapping("{id}")
    public void deleteStaff(@PathVariable Long id, @RequestBody Staff staff){
        log.info("deleteStaff: id = {}, staff = {}", id, staff);
        staffService.deleteStaff(id, staff);
    }
    //upload photo
    @PostMapping("/{id}")
    public void uploadPhoto(@PathVariable Long id, @RequestParam MultipartFile file) {
        log.info("uploadPhoto: id = {}, file = {}", id, file);
        byte[] bytes = null;
        try {
            bytes = file.getBytes();
        } catch (IOException ex) { }
        staffService.addPhoto(id, bytes);
    }
    //download photo
    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> getPhoto(@PathVariable Long id){
       return ResponseEntity.ok()
               .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg")
               .contentType(MediaType.IMAGE_JPEG)
               .body(staffService.getPhoto(id));
    }
    //delete photo
    @DeleteMapping("/{id}/delete")
    public void deletePhoto(@PathVariable Long id){
        staffService.deletePhoto(id);
    }
}
