package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.exceptions.EmptyFileException;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

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

    public Staff uploadImage(Long id, MultipartFile file) {

        if (file == null) {
            throw new EmptyFileException();
        }

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) { //if not found
            uploadDir.mkdir(); //then create directory
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "_" + file.getOriginalFilename();

        try {
            file.transferTo(new File(uploadPath + "/" + resultFilename));
        } catch (IOException e) {
            e.printStackTrace();
        }
       return staffRepo.save(uploadImage(id, file));
    }

    public Staff getImage(Long id, MultipartFile file) {
        return staffRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }
}
