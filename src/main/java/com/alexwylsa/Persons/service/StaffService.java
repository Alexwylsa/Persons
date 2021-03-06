package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.domain.StaffInDto;
import com.alexwylsa.Persons.exceptions.*;
import com.alexwylsa.Persons.repo.DepartmentRepo;
import com.alexwylsa.Persons.repo.StaffRepo;
import com.alexwylsa.Persons.repo.UserRepo;
import com.alexwylsa.Persons.parameters.SortParameters;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class StaffService {
    @Autowired
    private StaffRepo staffRepo;
    @Autowired
    private SortParameters sortParameters;
    @Value("${upload.path}")
    private String uploadPath;
    @Autowired
    private DepartmentRepo departmentRepo;
    @Autowired
    private UserRepo userRepo;
    //get all departments with parameters
    public List<Staff> getAllStaff(Optional<String> lastName, Integer page, Integer size, String byColumn,
                                   Integer ascending) {
        log.debug("getAllStaff: name = {}, page = {}, size = {}, byColumn = {}, ascending = {}", lastName,
                page, size, byColumn, ascending);
        PageRequest pagination = sortParameters.parameters(page, size, byColumn, ascending);
        return lastName.map(u->staffRepo.findAllByLastNameContainingIgnoreCase(u, pagination).getContent())
                .orElseGet(()->staffRepo.findAll(pagination).getContent());
    }
    //count staff
    public Long getStaffCount(Optional<String> firstName) {
        log.debug("getUsersCount: username = {}", firstName);
        return firstName.map(s -> staffRepo.countByFirstNameContains(s))
                .orElseGet(() -> staffRepo.count());
    }
    //get one staff
    public Staff getStaff(Long id) {
        log.debug("getStaff: id = {} ", id);
        return staffRepo.findById(id).orElseThrow(() -> new RestException(ErrorCodes.STAFF_NOT_FOUND));
    }
    //add new staff
    public Staff addStaff(StaffInDto staffInData) {
        log.debug("addStaff: staffInData = {} ", staffInData);
        Staff staff = new Staff();
        staff.setCreationDate(LocalDateTime.now());
        staff.setAge(staffInData.getAge());
        staff.setFirstName(staffInData.getFirstName());
        staff.setLastName(staffInData.getLastName());
        staff.setMail(staffInData.getMail());
        staff.setSex(staffInData.getSex());
        staff.setDepartment(departmentRepo.findById(staffInData.getDepartment_id())
                .orElseThrow(() -> new RestException(ErrorCodes.DEPARTMENT_NOT_EXIST)));
        if(staffInData.getUser_id()!=null) {
            staff.setUser(userRepo.findById(staffInData.getUser_id())
                    .orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST)));
        }
        return staffRepo.save(staff);
    }
    //update staff
    public Staff updateStaff(Long id, StaffInDto staffInData) {
        log.debug("updateStaff: id = {}, staff = {}", id, staffInData);
        Staff staffFromDb = staffRepo.findById(id)
                .orElseThrow(() -> new RestException(ErrorCodes.STAFF_NOT_FOUND));
        staffFromDb.setAge(staffInData.getAge());
        staffFromDb.setFirstName(staffInData.getFirstName());
        staffFromDb.setLastName(staffInData.getLastName());
        staffFromDb.setMail(staffInData.getMail());
        staffFromDb.setSex(staffInData.getSex());
        staffFromDb.setDepartment(departmentRepo.findById(staffInData.getDepartment_id())
                .orElseThrow(() -> new RestException(ErrorCodes.DEPARTMENT_NOT_EXIST)));
        staffFromDb.setUser(userRepo.findById(staffInData.getUser_id())
                .orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST)));
        return staffRepo.save(staffFromDb);
    }
    //delete staff
    public void deleteStaff(Long id, Staff staff) {
        log.debug("deleteStaff: id = {}, staff = {}", id, staff);
        try {Files.delete(Paths.get(uploadPath + "/" + id + ".jpg"));}
        catch (IOException e){throw new RestException(ErrorCodes.FILE_NOT_FOUND);}
        staffRepo.delete(staff);
    }
    //add new photo
    public void addPhoto(Long id, byte[] file) {
        log.debug("deleteStaff: id = {}, file = {}", id, file);
        try {
            Files.write(Paths.get(uploadPath + "/" + id + ".jpg"), file);
            Staff staffFromDb = staffRepo.findById(id)
                    .orElseThrow(() -> new RestException(ErrorCodes.STAFF_NOT_FOUND));
            staffFromDb.setPhotoFilePath(id.toString());
            staffRepo.save(staffFromDb);
        }
        catch (IOException ex) {throw new RestException(ErrorCodes.FILE_SENDING_FAILED);}
    }
    //get photo
    public byte[] getPhoto(Long id) {
        try {
            return Files.readAllBytes(new File(uploadPath + "/" + id + ".jpg").toPath());
        }
        catch (IOException e){throw new RestException(ErrorCodes.FILE_NOT_FOUND);}
    }
    //delete photo
    public void deletePhoto(Long id) {
        try {
            Files.delete(Paths.get(uploadPath + "/" + id + ".jpg"));
            Staff staffFromDb = staffRepo.findById(id)
                    .orElseThrow(() -> new RestException(ErrorCodes.STAFF_NOT_FOUND));
            staffFromDb.setPhotoFilePath("");
            staffRepo.save(staffFromDb);
        }
        catch (IOException e){throw new RestException(ErrorCodes.FILE_NOT_FOUND);}
    }
}
