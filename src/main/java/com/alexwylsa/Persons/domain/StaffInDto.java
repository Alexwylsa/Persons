package com.alexwylsa.Persons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//staff DTO
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StaffInDto {
    private String age;
    private String firstName;
    private String lastName;
    private String mail;
    private String sex;
    private Long department_id;
    private Long user_id;

}
