package com.registry.technicalassessment.mapper;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.LocalDate;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserMapperTest {

    @Test
    public void shouldReturnUserDtoFromUSer() {
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

        UserDto userDto = UserMapper.mapUserDtoFromUser(user_1);

        assertEquals(userDto.getName(),user_1.getName());
        assertEquals(userDto.getBirthDate(),user_1.getBirthDate());
    }

    @Test
    public void shouldReturnNullDtoForNullUser(){
        User user = null;

        UserDto userDto = UserMapper.mapUserDtoFromUser(user);

        assertNull(userDto);
    }

    @Test
    public void shouldReturnUserFromUserDto() {
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

        User user = UserMapper.mapUserFromUserDto(userDto_1);

        assertEquals(user.getName(),userDto_1.getName());
        assertEquals(user.getBirthDate(),userDto_1.getBirthDate());
    }

    @Test
    public void shouldReturnNullUserFromNullDto(){
        UserDto userDto = null;

        User user = UserMapper.mapUserFromUserDto(userDto);

        assertNull(user);
    }
}