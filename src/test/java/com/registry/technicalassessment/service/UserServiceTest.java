package com.registry.technicalassessment.service;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.mapper.UserMapper;
import com.registry.technicalassessment.model.User;
import com.registry.technicalassessment.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;



    @Test
    public void shouldReturnAllUsers() {
        List<UserDto> mockUsersDto = getUsersDto();
        List<User> mockUsers = getUsers();

        when(userRepository.findAll()).thenReturn(mockUsers);
        when(userMapper.userToUserDto(mockUsers.get(0))).thenReturn(mockUsersDto.get(0));
        when(userMapper.userToUserDto(mockUsers.get(1))).thenReturn(mockUsersDto.get(1));

        List<UserDto> usersDto = userService.retrieveAllUsers();

        assertEquals(usersDto.get(0).getName(),mockUsersDto.get(0).getName());
        assertEquals(usersDto.get(1).getBirthDate(),mockUsers.get(1).getBirthDate());
    }

    @Test
    public void shouldReturnUsersByName(){
        String name = "youness";
        List<UserDto> mockUsersDto = getUsersDto()
                .stream()
                .filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());
        List<User> mockUsers = getUsers()
                .stream()
                .filter(x -> x.getName().equals(name))
                .collect(Collectors.toList());

        when(userRepository.findUserByName(name)).thenReturn(mockUsers);
        when(userMapper.userToUserDto(mockUsers.get(0))).thenReturn(mockUsersDto.get(0));

        List<UserDto> usersDto = userService.retrieveUserByName(name);

        assertEquals(usersDto.get(0).getName(),mockUsersDto.get(0).getName());
        assertEquals(usersDto.get(0).getBirthDate(),mockUsers.get(0).getBirthDate());
    }

    @Test
    public void shouldReturnUserById(){
        long id = 1;
        UserDto mockUserDto = getUsersDto().get(0);
        User mockUser = getUsers().get(0);

        when(userRepository.findById(id)).thenReturn(Optional.of(mockUser));
        when(userMapper.userToUserDto(mockUser)).thenReturn(mockUserDto);

        UserDto userDto = userService.retrieveUserById(id);

        assertEquals(userDto.getName(),mockUserDto.getName());
        assertEquals(userDto.getBirthDate(),mockUser.getBirthDate());
    }

    @Test
    public void shouldSaveUser(){
        UserDto mockUserDto = getUsersDto().get(0);
        User mockUser = getUsers().get(0);

        when(userRepository.save(any(User.class))).thenReturn(mockUser);
        when(userMapper.userDtoToUser(mockUserDto)).thenReturn(mockUser);
        when(userMapper.userToUserDto(any(User.class))).thenReturn(mockUserDto);
        UserDto userDto = userService.saveUser(mockUserDto);

        assertEquals(userDto.getName(),mockUserDto.getName());
        assertEquals(userDto.getBirthDate(),mockUser.getBirthDate());
    }

    private List<User> getUsers(){
        User user_1 = new User();
        user_1.setId(1);
        user_1.setName("youness");
        user_1.setCountry("france");
        user_1.setBirthDate(LocalDate.of(1997,7,14));
        user_1.setPhoneNumber("+363798876543");

        User user_2 = new User();
        user_2.setId(2);
        user_2.setName("eddy");
        user_2.setCountry("fr");
        user_2.setBirthDate(LocalDate.of(1990,6,1));
        user_2.setPhoneNumber("+363798876543");

        return Arrays.asList(user_1,user_2);
    }

    private List<UserDto> getUsersDto(){
        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("france");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");

        UserDto userDto_2 = new UserDto();
        userDto_2.setName("eddy");
        userDto_2.setCountry("fr");
        userDto_2.setBirthDate(LocalDate.of(1990,6,1));
        userDto_2.setPhoneNumber("+363798876543");

        return Arrays.asList(userDto_1,userDto_2);
    }
}