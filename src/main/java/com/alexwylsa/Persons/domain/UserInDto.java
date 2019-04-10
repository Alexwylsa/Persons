package com.alexwylsa.Persons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//user DTO
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInDto {
    private String username;
    private String password;
}
