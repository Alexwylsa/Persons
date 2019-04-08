package com.alexwylsa.Persons;

import com.alexwylsa.Persons.domain.Role;
import com.alexwylsa.Persons.domain.User;
import com.alexwylsa.Persons.repo.StaffRepo;
import com.alexwylsa.Persons.repo.UserRepo;
import com.alexwylsa.Persons.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonsApplicationTests {

//	@Autowired
//	private StaffRepo staffRepo;
//
//	@Autowired
//	private UserRepo userRepo;
//
//	@Autowired
//	private UserService userService;

//	@Test
//	public void contextLoads() {
//		System.out.println(staffRepo.findAllByOrderByFirstNameAsc());
//	}

//	@Test
//	public void testLoadUserByUsername() {
//		userRepo.save(new User("user1", "password1", new ArrayList<>(Arrays.asList(Role.ADMIN))));
//		UserDetails ud = userRepo.loadUserByUsername("user1");
//
//		Assert.assertEquals("ud.getUsername() = " + ud.getUsername(), "user1", ud.getUsername());
//		Assert.assertEquals("ud.getPassword() = " + ud.getPassword(), "password1", ud.getPassword());
//	}

//	@Test
//	public void  testCountUser(){
//		userRepo.count();
//		CountUser cu = userService.getUsersCount(Optional.empty());
//	}

//	@Test
//	public void test_get_all_success() throws Exception {
//		List<User> users = Arrays.asList(
//				new User(1, "Daenerys", "123"),
//				new User(2, "Joh Snow", "123"));
//		when(userService.getAll()).thenReturn(users);
//		mockMvc.perform(get("/users"))
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$", hasSize(2)))
//				.andExpect(jsonPath("$[0].id", is(1)))
//				.andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
//				.andExpect(jsonPath("$[1].id", is(2)))
//				.andExpect(jsonPath("$[1].username", is("John Snow")));
//		verify(userService, times(1)).getAll();
//		verifyNoMoreInteractions(userService);
//	}

//	@Test
//	public void test_create_user_success() throws Exception {
//		User user = new User("Arya", "123");
//		when(userService.exists(user)).thenReturn(false);
//		doNothing().when(userService).create(user);
//		mockMvc.perform(
//				post("/users")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(asJsonString(user)))
//				.andExpect(status().isCreated())
//				.andExpect(header().string("location", containsString("http://localhost/users/")));
//		verify(userService, times(1)).exists(user);
//		verify(userService, times(1)).create(user);
//		verifyNoMoreInteractions(userService);
//	}
}
