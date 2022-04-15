package com.registry.technicalassessment.mapper;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void shouldReturnUserDtoFromUser() {
        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("france");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");

        User user_1 = new User();
        user_1.setId(1);
        user_1.setName("youness");
        user_1.setCountry("france");
        user_1.setBirthDate(LocalDate.of(1997,7,14));
        user_1.setPhoneNumber("+363798876543");

        UserDto userDto = userMapper.userToUserDto(user_1);

        assertEquals(userDto.getName(),user_1.getName());
        assertEquals(userDto.getBirthDate(),user_1.getBirthDate());
    }

    @Test
    void shouldReturnNullDtoForNullUser(){
        assertNull(userMapper.userToUserDto(null));
    }

    @Test
    void shouldReturnUserFromUserDto() {
        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("france");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");

        User user_1 = new User();
        user_1.setId(1);
        user_1.setName("youness");
        user_1.setCountry("france");
        user_1.setBirthDate(LocalDate.of(1997,7,14));
        user_1.setPhoneNumber("+363798876543");

        User user = userMapper.userDtoToUser(userDto_1);

        assertEquals(user.getName(),userDto_1.getName());
        assertEquals(user.getBirthDate(),userDto_1.getBirthDate());
    }

    @Test
    void shouldReturnNullUserFromNullDto(){
        assertNull(userMapper.userToUserDto(null));
    }
}