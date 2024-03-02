package org.example.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import org.example.entity.CustomBirthday;
import org.example.entity.Payment;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.entity.UserChat;
import org.hibernate.annotations.BatchSize;

//DTO to send to DB
public record UserCreateDto(Integer id,
		 @NotNull String userName,
			 String firstName,
			 String lastname,
			 LocalDate birthDate,
			 Role role,
			 CustomBirthday customBirthday,
		   @Valid PersonalInfo personalInfo,
			 CompanyReadDto company,
			 Integer age,
			 List<Payment> payments,
			 List<UserChat> userChats
)  {}
