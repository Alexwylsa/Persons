package com.alexwylsa.Persons.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @ManyToOne()
    @JoinColumn(name="department_id")
    private Department department;

    private String mail;

    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView
    private LocalDateTime creationDate;
}
