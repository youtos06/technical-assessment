package com.registry.technicalassessment.mapper;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.model.Country;
import com.registry.technicalassessment.model.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class UserMapperTest {

    @Spy
    UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldReturnUserDtoFromUser() {
        User user = new User();
        user.setId(1);
        user.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user.setCountry(country);
        user.setBirthDate(LocalDate.of(1997,7,14));
        user.setPhoneNumber("+363798876543");

        UserDto userDto = userMapper.userToUserDto(user);

        assertNotNull(userDto);
        assertEquals(user.getName(),userDto.getName());
        assertEquals(user.getBirthDate(),userDto.getBirthDate());
    }

    @Test
    void shouldReturnNullDtoForNullUser(){
        assertNull(userMapper.userToUserDto(null));
    }

    @Test
    void shouldReturnUserFromUserDto() {
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");


        User user = userMapper.userDtoToUser(userDto);

        assertNotNull(user);
        assertEquals(userDto.getName(),user.getName());
        assertEquals(userDto.getBirthDate(),user.getBirthDate());
        assertEquals(userDto.getCountry(),user.getCountry().getCode());
    }

    @Test
    void shouldReturnNullUserFromNullDto(){
        assertNull(userMapper.userToUserDto(null));
    }
}