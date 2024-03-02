package org.example.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.dao.UserRepository;
import org.example.dto.UserCreateDto;
import org.example.dto.UserReadDto;
import org.example.entity.User;
import org.example.mapper.UserCreateMapper;
import org.example.mapper.UserReadMapper;

@RequiredArgsConstructor
public class UserService {

    private UserRepository repository;
    private UserReadMapper userReadMapper;
    private UserCreateMapper userCreateMapper;

    public Boolean delete(Integer id) {
        final Optional<User> userFromDB = repository.findById(id);
        userFromDB.ifPresent(user -> repository.delete(id));

        return userFromDB.isPresent();
    }

    public Optional<UserReadDto> findUserById(Integer id) {
        final Optional<User> userById = repository.findById(id);

        final Optional<UserReadDto> userReadDto = userById.map(userReadMapper::mapFrom);

        return userReadDto;
    }

    public Integer create(UserCreateDto userDto){
        final User user = userCreateMapper.mapFrom(userDto);
        final User savedUser = repository.save(user);

        return savedUser.getId();
    }


}
