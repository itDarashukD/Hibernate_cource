package org.example.entity;


import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    private String userName;

    private String firstName;

    private String lastname;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private Integer age;
    @Enumerated(EnumType.STRING)
    private Role role;

}
