package org.example.service;

import java.util.Optional;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
// validation :
        //all validation exceptions will be here in this SET
        final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        final Validator validator = validatorFactory.getValidator();
        final Set<ConstraintViolation<UserCreateDto>> validationResult = validator.validate(userDto);
        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }


        final User user = userCreateMapper.mapFrom(userDto);
        final User savedUser = repository.save(user);

        return savedUser.getId();
    }


}
