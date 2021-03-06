package com.alexwylsa.Persons.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
//staff entity
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
    @ManyToOne()
    @JoinColumn(name="department_id")
    private Department department;
    @Column(updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonView
    private LocalDateTime creationDate;
    private String firstName;
    private String lastName;
    private String age;
    private String sex;
    private String mail;
    private String photoFilePath;
}
