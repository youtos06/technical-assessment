package com.registry.technicalassessment.mapper;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User mapUserFromUserDto(UserDto userDto){
        if (userDto == null){
            return null;
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setGender(userDto.getGender());
        user.setBirthDate(userDto.getBirthDate());
        user.setCountry(userDto.getCountry());
        user.setPhoneNumber(userDto.getPhoneNumber());

        return user;
    }

    public UserDto mapUserDtoFromUser(User user){
        if (user == null){
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setGender(user.getGender());
        userDto.setBirthDate(user.getBirthDate());
        userDto.setCountry(user.getCountry());
        userDto.setPhoneNumber(user.getPhoneNumber());

        return userDto;
    }
}
