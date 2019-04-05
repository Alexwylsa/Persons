package com.alexwylsa.Persons;

import com.alexwylsa.Persons.repo.StaffRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonsApplicationTests {

	@Autowired
	private StaffRepo staffRepo;

	@Test
	public void contextLoads() {
		System.out.println(staffRepo.findAllByOrderByFirstNameAsc());
	}

}
