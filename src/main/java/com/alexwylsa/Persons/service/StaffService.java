package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;

import com.alexwylsa.Persons.exceptions.FileStorageException;
import com.alexwylsa.Persons.exceptions.NotFoundException;
import com.alexwylsa.Persons.repo.StaffRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class StaffService {
    @Autowired
    private StaffRepo staffRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public List<Staff> getAllStaff(Optional<String> lastName, Integer page, Integer size) {
        log.debug("getAllStaff: name = {}, page = {}, size = {}", lastName, page, size);
        Pageable pagination = PageRequest.of(page, size);
        return lastName.map(s->staffRepo.findAllByLastNameContainingIgnoreCase(s, pagination)).orElseGet(()->staffRepo
                .findAll(pagination)).getContent();
    }

    public Staff getStaff(Long id) {
        log.debug("getStaff: id = {} ", id);
        return staffRepo.findById(id).orElseThrow(() -> new NotFoundException());
    }

    public Staff addStaff(Staff staff) {
        log.debug("addStaff: staff = {} ", staff);
        return staffRepo.save(staff);
    }

    public Staff updateStaff(Long id, Staff staff) {
        log.debug("updateStaff: id = {}, staff = {}", id, staff);
        return staffRepo.save(staff);
    }

    public void deleteStaff(Long id, Staff staff) {
        log.debug("deleteStaff: id = {}, staff = {}", id, staff);
        staffRepo.delete(staff);
    }


    public void addPhoto(Long id, byte[] file) {
        log.debug("deleteStaff: id = {}, file = {}", id, file);
        try {
            Files.write(Paths.get(uploadPath + "/" + id + ".jpg"), file);
        } catch (IOException ex) {throw new FileStorageException("Sending failed", ex);}

        Staff staff = staffRepo.findById(id).orElseThrow(NotFoundException::new);
        staff.setPhotoFilePath(id.toString());
    }

//
//    public Staff getPhoto(Long id, MultipartFile file) {
//        return staffRepo.findById(id).orElseThrow(() -> new NotFoundException());
//    }
//
//    public void deletePhoto(MultipartFile file) {
//        staffRepo.delete(file);
//    }
}
