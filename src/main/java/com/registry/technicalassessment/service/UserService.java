package com.registry.technicalassessment.service;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.mapper.UserMapper;
import com.registry.technicalassessment.model.User;
import com.registry.technicalassessment.repository.UserRepository;
import com.registry.technicalassessment.validator.UserValidator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    /**
     * Returns list of users in db that have a certain name
     *
     * @param name parameter of User
     * @throws BusinessApiException if we found no user with the searched name and status NOT FOUND
     * @return list of users with name equal to name argument
     */
    public List<UserDto> retrieveUserByName(String name){
        List<User> users = userRepository.findUserByName(name);
        if (users.isEmpty()){
            throw new BusinessApiException(String.format("No users with the following name : %s",name), HttpStatus.NOT_FOUND);
        }
        return users.stream().map(userMapper::userToUserDto).collect(Collectors.toList());
    }


    /**
     * Returns list of all users in our db
     *
     * @throws  BusinessApiException if no user is saved with status OK
     * @return  list of all users
     */
    public List<UserDto> retrieveAllUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new BusinessApiException("No user is currently saved", HttpStatus.OK);
        }
        return users.stream().map(userMapper::userToUserDto).collect(Collectors.toList());
    }

    /**
     * Returns one user based on his ID
     *
     * @param id key of the user
     * @throws BusinessApiException if no user with id and status NOT FOUND
     * @return user with id equal to id argument
     */
    public UserDto retrieveUserById(long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new BusinessApiException(String.format("No users with the following id: %s",id), HttpStatus.NOT_FOUND);
        }
        return userMapper.userToUserDto(userOptional.get());
    }

    /**
     * Save user in database if not exist
     *
     * @param userDto user details to be saved as user
     * @throws BusinessApiException if the user already in database
     * @return dto of user after it was saved
     */
    public UserDto saveUser(UserDto userDto){
        userValidator.validate(userDto);
        List<User> users = userRepository.findUserByNameAndBirthDateAndCountryCode(
                userDto.getName(),
                userDto.getBirthDate(),
                userDto.getCountry());
        // Heavy check - can be avoided depending on business logic
        if (!users.isEmpty()){
            throw new BusinessApiException(
                    String.format("found a user with same with the following name  %s, birth date %s And country %s",
                            userDto.getName(),
                            userDto.getBirthDate(),
                            userDto.getCountry()),
                    HttpStatus.CONFLICT);
        }
        User user = userMapper.userDtoToUser(userDto);
        return userMapper.userToUserDto(userRepository.save(user));
    }
}
