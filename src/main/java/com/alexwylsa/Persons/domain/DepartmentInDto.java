package com.alexwylsa.Persons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//department DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentInDto {
    private String name;
    private Long bossId;
}
