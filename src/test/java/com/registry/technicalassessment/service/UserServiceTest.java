package com.registry.technicalassessment.service;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.mapper.UserMapper;
import com.registry.technicalassessment.model.Country;
import com.registry.technicalassessment.model.User;
import com.registry.technicalassessment.repository.UserRepository;
import com.registry.technicalassessment.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private UserValidator userValidator;

    @Test
    void shouldReturnAllUsers() {
        List<UserDto> usersDto = getUsersDto();
        List<User> users = getUsers();

        when(userRepository.findAll()).thenReturn(users);
        when(userMapper.userToUserDto(users.get(0))).thenReturn(usersDto.get(0));
        when(userMapper.userToUserDto(users.get(1))).thenReturn(usersDto.get(1));

        List<UserDto> retrieveUsers = userService.retrieveAllUsers();

        assertEquals(usersDto.get(0).getName(),retrieveUsers.get(0).getName());
        assertEquals(usersDto.get(0).getGender(),retrieveUsers.get(0).getGender());
        assertEquals(usersDto.get(0).getPhoneNumber(),retrieveUsers.get(0).getPhoneNumber());
        assertEquals(usersDto.get(0).getCountry(),retrieveUsers.get(0).getCountry());
        assertEquals(usersDto.get(1).getBirthDate(),retrieveUsers.get(1).getBirthDate());
        assertEquals(usersDto.get(1).getGender(),retrieveUsers.get(1).getGender());
        assertEquals(usersDto.get(1).getPhoneNumber(),retrieveUsers.get(1).getPhoneNumber());
        assertEquals(usersDto.get(1).getCountry(),retrieveUsers.get(1).getCountry());
    }

    @Test
    void shouldReturnUsersByName(){
        User user = new User();
        user.setId(1);
        user.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user.setCountry(country);
        user.setBirthDate(LocalDate.of(1997,7,14));
        user.setPhoneNumber("+363798876543");

        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        when(userRepository.findUserByName("youness")).thenReturn(Collections.singletonList(user));
        when(userMapper.userToUserDto(any(User.class))).thenReturn(userDto);

        List<UserDto> retrieveUsers = userService.retrieveUserByName("youness");

        verify(userRepository,times(1)).findUserByName(anyString());

        assertEquals(userDto.getName(),retrieveUsers.get(0).getName());
        assertEquals(userDto.getGender(),retrieveUsers.get(0).getGender());
        assertEquals(userDto.getPhoneNumber(),retrieveUsers.get(0).getPhoneNumber());
        assertEquals(userDto.getCountry(),retrieveUsers.get(0).getCountry());
    }

    @Test
    void shouldReturnUserById(){
        long id = 1;
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        User user = new User();
        user.setId(1);
        user.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user.setCountry(country);
        user.setBirthDate(LocalDate.of(1997,7,14));
        user.setPhoneNumber("+363798876543");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userMapper.userToUserDto(user)).thenReturn(userDto);

        UserDto userRetrieved = userService.retrieveUserById(id);

        assertEquals(userDto.getName(),userRetrieved.getName());
        assertEquals(userDto.getBirthDate(),userRetrieved.getBirthDate());
        assertEquals(userDto.getCountry(),userRetrieved.getCountry());
    }

    @Test
    void shouldSaveUser(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        User user = new User();
        user.setId(1);
        user.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user.setCountry(country);
        user.setBirthDate(LocalDate.of(1997,7,14));
        user.setPhoneNumber("+363798876543");

        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.userDtoToUser(userDto)).thenReturn(user);
        when(userMapper.userToUserDto(any(User.class))).thenReturn(userDto);

        UserDto retrievedUser = userService.saveUser(userDto);
        assertEquals(userDto.getName(),retrievedUser.getName());
        assertEquals(userDto.getBirthDate(),retrievedUser.getBirthDate());
    }

    @Test
    void throwBusinessException_when_no_users(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("IT");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        when(userRepository.findAll()).thenReturn(Collections.emptyList());
        Exception exception = assertThrows(BusinessApiException.class, () -> userService.retrieveAllUsers());
        assertEquals("No user is currently saved",exception.getMessage());
    }

    @Test
    void throwBusinessException_when_no_user_by_name(){
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("IT");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        Exception exception = assertThrows(BusinessApiException.class, () -> userService.retrieveUserByName("youness"));
        assertEquals(String.format("No users with the following name : %s",userDto.getName()),exception.getMessage());
    }

    @Test
    void throwBusinessException_when_no_user_by_id(){
        User user = new User();
        user.setId(1);
        user.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user.setCountry(country);
        user.setBirthDate(LocalDate.of(1997,7,14));
        user.setPhoneNumber("+363798876543");

        Exception exception = assertThrows(BusinessApiException.class, () -> userService.retrieveUserById(1));
        assertEquals(String.format("No users with the following id: %s",user.getId()),exception.getMessage());
    }

    @Test
    void throwBusinessException_when_user_exist(){
        User user = new User();
        user.setId(1);
        user.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user.setCountry(country);
        user.setBirthDate(LocalDate.of(1997,7,14));
        user.setPhoneNumber("+363798876543");

        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("IT");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        when(userRepository.findUserByNameAndBirthDateAndCountryCode(
                userDto.getName(),
                userDto.getBirthDate(),
                userDto.getCountry()
                )).thenReturn(Collections.singletonList(user));
        Exception exception = assertThrows(BusinessApiException.class, () -> userService.saveUser(userDto));
        assertEquals(String.format("found a user with same with the following name  %s, birth date %s And country %s",
                userDto.getName(),
                userDto.getBirthDate(),
                userDto.getCountry())
                ,exception.getMessage());
    }

    private List<User> getUsers(){
        User user_1 = new User();
        user_1.setId(1);
        user_1.setName("youness");
        Country country = new Country();
        country.setCode("FR");
        user_1.setCountry(country);
        user_1.setBirthDate(LocalDate.of(1997,7,14));
        user_1.setPhoneNumber("+363798876543");

        User user_2 = new User();
        user_2.setId(2);
        user_2.setName("eddy");
        user_2.setCountry(country);
        user_2.setBirthDate(LocalDate.of(1990,6,1));
        user_2.setPhoneNumber("+363798876543");

        return Arrays.asList(user_1,user_2);
    }

    private List<UserDto> getUsersDto(){
        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("FR");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");

        UserDto userDto_2 = new UserDto();
        userDto_2.setName("eddy");
        userDto_2.setCountry("FR");
        userDto_2.setBirthDate(LocalDate.of(1990,6,1));
        userDto_2.setPhoneNumber("+363798876543");

        return Arrays.asList(userDto_1,userDto_2);
    }
}