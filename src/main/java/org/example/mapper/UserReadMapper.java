package org.example.mapper;


import lombok.RequiredArgsConstructor;
import org.example.dto.UserReadDto;
import org.example.entity.User;

@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    private CompanyReadMapper companyReadMapper;

    @Override
    public UserReadDto mapFrom(User from) {

        return new UserReadDto(from.getId(),
	       from.getUserName(),
	       from.getFirstName(),
	       from.getLastname(),
	       from.getBirthDate(),
	       from.getRole(),
	       from.getCustomBirthday(),
	       from.getPersonalInfo(),
	       companyReadMapper.mapFrom(from.getCompany()),
	       from.getAge(),
	       from.getUserChats()
        );
    }

}
