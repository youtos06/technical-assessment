package com.registry.technicalassessment.service;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.mapper.UserMapper;
import com.registry.technicalassessment.model.User;
import com.registry.technicalassessment.repository.UserRepository;
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


    public List<UserDto> retrieveUserByName(String name){
        List<User> users = userRepository.findUserByName(name);
        if (users.isEmpty()){
            throw new BusinessApiException(String.format("No users with the following name : %s",name), HttpStatus.NOT_FOUND);
        }
        return users.stream().map(user -> UserMapper.mapUserDtoFromUser(user)).collect(Collectors.toList());
    }

    public List<UserDto> retrieveAllUsers(){
        List<User> users = userRepository.findAll();
        if (users.isEmpty()){
            throw new BusinessApiException(String.format("No user is currently saved"), HttpStatus.OK);
        }
        return users.stream().map(user -> UserMapper.mapUserDtoFromUser(user)).collect(Collectors.toList());
    }

    public UserDto retrieveUserById(long id){
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new BusinessApiException(String.format("No users with the following id: %s",id), HttpStatus.NOT_FOUND);
        }
        return UserMapper.mapUserDtoFromUser(userOptional.get());
    }

    public UserDto saveUser(UserDto userDto){
        List<User> users = userRepository.findUserByNameAndBirthDate(userDto.getName(),userDto.getBirthDate());
        // Heavy check - can be avoided depending on business logic
        if (!users.isEmpty()){
            throw new BusinessApiException(
                    String.format("found a user with same with the following name  %s and birth date %s",
                            userDto.getName(),
                            userDto.getBirthDate()),
                    HttpStatus.CONFLICT);
        }
        User user = UserMapper.mapUserFromUserDto(userDto);
        return UserMapper.mapUserDtoFromUser(userRepository.save(user));
    }
}
