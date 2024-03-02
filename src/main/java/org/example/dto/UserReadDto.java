package org.example.dto;

import java.time.LocalDate;
import java.util.List;
import org.example.entity.Company;
import org.example.entity.CustomBirthday;
import org.example.entity.Payment;
import org.example.entity.PersonalInfo;
import org.example.entity.Role;
import org.example.entity.UserChat;

public record UserReadDto(Integer id,
		        String userName,
		        String firstName,
		        String lastname,
		        LocalDate birthDate,
		        Role role,
		        CustomBirthday customBirthday,
		        PersonalInfo personalInfo,
		        CompanyReadDto company,
		        Integer age,
		        List<Payment> payments,
		        List<UserChat> userChats
		        )  {}
