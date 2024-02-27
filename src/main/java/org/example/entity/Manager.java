package org.example.entity;


import java.time.LocalDate;
import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Manager extends User {

    private String project;

    @Builder
    public Manager(Integer id,
	   String userName,
	   String firstName,
	   String lastname,
	   LocalDate birthDate,
	   Integer age,
	   Role role,
	   CustomBirthday customBirthday,
	   PersonalInfo personalInfo,
	   Company company,
	   Profile profile,
	   List<UserChat> userChats,
	   String project) {
        super(id,
	       userName,
	       firstName,
	       lastname,
	       birthDate,
	       age,
	       role,
	       customBirthday,
	       personalInfo,
	       company,
	       profile,
	       userChats);
        this.project = project;
    }
}
