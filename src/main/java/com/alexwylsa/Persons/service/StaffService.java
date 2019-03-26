package com.alexwylsa.Persons.service;

import com.alexwylsa.Persons.domain.Staff;
import com.alexwylsa.Persons.exeptions.NotFoundException;
import com.alexwylsa.Persons.repo.StaffRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepo staffRepo;

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
}
