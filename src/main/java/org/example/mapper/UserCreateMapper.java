package org.example.mapper;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.dao.CompanyRepository;
import org.example.dto.UserCreateDto;
import org.example.entity.Company;
import org.example.entity.User;

@RequiredArgsConstructor
public class UserCreateMapper implements Mapper<UserCreateDto, User>{

    private CompanyRepository companyRepository;

    @Override
    public User mapFrom(UserCreateDto from) {
        final Optional<Company> companyById = companyRepository.findById(from.company().id());

        return User.builder()
	       .age(from.age())
	       .company(companyById.orElseThrow(IllegalStateException :: new)) //if company is not present in DB
	       .personalInfo(from.personalInfo())
	       .role(from.role())
	       .customBirthday(from.customBirthday())
	       .userName(from.userName())
	       .lastname(from.lastname())
	       .payments(from.payments())
	       .userChats(from.userChats())
	       .build();
    }
}
