package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;

import com.alexwylsa.Persons.exceptions.FileStorageException;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepo staffRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public List<Staff> getAllStaff() {
        return staffRepo.findAll();
    }

    public Staff getStaff(Long id) {
        return staffRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public Staff addStaff(Staff staff) {
        return staffRepo.save(staff);
    }

    public Staff updateStaff(Staff staff) {
        return staffRepo.save(staff);
    }

    public void deleteStaff(Staff staff) {
        staffRepo.delete(staff);
    }


    public void addPhoto(Long staffId, MultipartFile file) {
        try {
            Files.createFile(Paths.get(""));
        } catch (IOException ex) {throw new FileStorageException("Sending failed", ex);}

        Staff staff = staffRepo.findById(staffId).orElseThrow(NotFoundException::new);
        staff.setPhotoFilePath(file.toString());
    }
}
