package com.registry.technicalassessment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.ApiExceptionHandler;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.holder.ApiPath;
import com.registry.technicalassessment.service.UserService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    MockMvc mockMvc;

    @Mock
    private UserService userService;

    ObjectMapper objectMapper ;

    @Before
    public void setUp(){
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JSR310Module());
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new ApiExceptionHandler()).build();
    }


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
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value(name));
    }

    @Test
    public void shouldSaveUser() throws Exception {
        UserDto userDto_1 = new UserDto();
        userDto_1.setName("youness");
        userDto_1.setCountry("FR");
        userDto_1.setBirthDate(LocalDate.of(1997,7,14));
        userDto_1.setPhoneNumber("+363798876543");
        when(userService.saveUser(any(UserDto.class))).thenReturn(userDto_1);
        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto_1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.country").value("FR"));
    }

    @Test
    public void throwBusinessException_when_save_nonFrench_user() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("MR");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        when(userService.saveUser(any(UserDto.class))).thenThrow(
                new BusinessApiException("Country not allowed : " + userDto.getCountry(), HttpStatus.BAD_REQUEST)
        );
        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(100))
                .andExpect(jsonPath("$.message").value(
                        "Country not allowed : " + userDto.getCountry()));
    }

    @Test
    public void shouldNotSaveNonAdultUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("MR");
        userDto.setBirthDate(LocalDate.of(2020,7,14));
        userDto.setPhoneNumber("+363798876543");

        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(102));
    }

    @Test
    public void throwBusinessException_when_user_notFound_byName() throws Exception {
        String name = "zoro";

        when(userService.retrieveUserByName(name)).thenThrow(
                new BusinessApiException(
                        String.format("No users with the following name : %s",name),
                        HttpStatus.NOT_FOUND)
        );

        mockMvc.perform(get(ApiPath.USER)
                .param("name",name))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof BusinessApiException))
                .andExpect(result -> assertEquals(String.format("No users with the following name : %s",name),
                        Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void throwBusinessException_when_save_existent_user() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("youness");
        userDto.setCountry("FR");
        userDto.setBirthDate(LocalDate.of(1997,7,14));
        userDto.setPhoneNumber("+363798876543");

        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated());

        when(userService.saveUser(any(UserDto.class))).thenThrow(
                new BusinessApiException(
                        String.format("found a user with same with the following name  %s, birth date %s And country %s",
                                userDto.getName(),
                                userDto.getBirthDate(),
                                userDto.getCountry()),
                        HttpStatus.CONFLICT)
        );
        mockMvc.perform(post(ApiPath.USER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value(100))
                .andExpect(jsonPath("$.message").value(
                        String.format("found a user with same with the following name  %s, birth date %s And country %s",
                                userDto.getName(),
                                userDto.getBirthDate(),
                                userDto.getCountry())
                ));
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