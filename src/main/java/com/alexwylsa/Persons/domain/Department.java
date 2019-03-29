package com.alexwylsa.Persons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "department")

public class Department {

    @Id
    @GeneratedValue()
    private Long id;
    private String department;

    @OneToOne()
    @JoinColumn(name = "boss_id")
    private Staff staff;



}
