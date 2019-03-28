package com.alexwylsa.Persons.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "staff")
public class Staff{

@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Long id;

    @OneToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private String first_name;

    private String last_name;

    private String age;

    private String sex;

    private String department_id;

    private String filename;

    private String mail;


}
