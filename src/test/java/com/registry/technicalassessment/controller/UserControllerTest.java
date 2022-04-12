package com.registry.technicalassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.holder.ApiPath;
import com.registry.technicalassessment.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.powermock.api.mockito.PowerMockito.when;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void shouldReturnAllUsers() throws Exception {
        List<UserDto> usersDto = getUsersDto();

        when(userService.retrieveAllUsers()).thenReturn(usersDto);

        mockMvc.perform(get(ApiPath.USER))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    public void shouldReturnOneUserById() throws Exception {
        when(userService.retrieveUserById(1)).thenReturn(getUsersDto().get(0));

        mockMvc.perform(get(ApiPath.USER+ApiPath.USER_BY_ID,1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("youness"));
    }

    @Test
    public void shouldReturnNotFoundForUserWrongId() throws Exception {
        when(userService.retrieveUserById(10))
                .thenThrow(new BusinessApiException(
                        String.format("No users with the following id: %s",10)
                        ,HttpStatus.NOT_FOUND)
                );

        mockMvc.perform(get(ApiPath.USER+ApiPath.USER_BY_ID,10))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No users with the following id: 10"));

    }

    @Test
    public void shouldReturnUsersByName() throws Exception {
        String name = "youness";
        List<UserDto> usersDto = List.of(getUsersDto().get(0));

        when(userService.retrieveUserByName(name)).thenReturn(usersDto);

        mockMvc.perform(get(ApiPath.USER)
                .param("name",name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    public void shouldReturnUserNotFoundByName() throws Exception {
        String name = "zoro";

        when(userService.retrieveUserByName(name)).thenThrow(
                new BusinessApiException(
                        String.format("No users with the following name : %s",name),
                        HttpStatus.NOT_FOUND)
        );

        mockMvc.perform(get(ApiPath.USER)
                .param("name",name))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No users with the following name : zoro"));
    }

    @Test
    public void shouldSaveUser() throws Exception {
        objectMapper.registerModule(new JSR310Module());


        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("france");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");
        when(userService.saveUser(any(UserDto.class))).thenReturn(userDto_1);
        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("youness"));
    }

    @Test
    public void shouldNotSaveUserWithSameNameAndBirthDate() throws Exception {
        objectMapper.registerModule(new JSR310Module());

        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("france");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");

        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto_1)))
                .andExpect(status().isCreated());

        when(userService.saveUser(any(UserDto.class))).thenThrow(
                new BusinessApiException(
                        String.format("found a user with same with the following name  %s and birth date %s",
                                userDto_1.getName(),
                                userDto_1.getBirthDate()),
                        HttpStatus.CONFLICT)
        );
        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto_1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(100));
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