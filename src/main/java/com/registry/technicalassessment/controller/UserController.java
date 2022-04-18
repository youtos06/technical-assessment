package com.registry.technicalassessment.controller;

import com.registry.technicalassessment.annotation.LogExecutionTime;
import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.exception.ApiExceptionResponse;
import com.registry.technicalassessment.exception.BusinessApiException;
import com.registry.technicalassessment.holder.ApiPath;
import com.registry.technicalassessment.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = ApiPath.USER)
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    @ApiOperation(value = "Retrieve all users or users with specific name")
    @ApiResponses(value = {
            @ApiResponse(code = 200 , response = UserDto.class , message = "Users retrieved with success"),
            @ApiResponse(code = 404 , response = ApiExceptionResponse.class , message = "No user with name equal to param name")
    })
    public ResponseEntity<List<UserDto>> getUsers(@ApiParam("Filter users by name") @RequestParam(required = false) String name) {
        List<UserDto> users;
        if (name != null) {
            users = userService.retrieveUserByName(name);
        } else {
            users = userService.retrieveAllUsers();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(
            value = ApiPath.USER_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    @ApiOperation(value = "Retrieve user by Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200 , response = UserDto.class , message = "Found user with id"),
            @ApiResponse(code = 404 , response = ApiExceptionResponse.class , message = "No user with the specified id")
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto user = userService.retrieveUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    @ApiOperation(value = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(code = 201 , response = UserDto.class , message = "Created User with success"),
            @ApiResponse(code = 409 , response = ApiExceptionResponse.class , message = "User exist already"),
            @ApiResponse(code = 400 , response = ApiExceptionResponse.class , message = "Body have invalid or missing param")
    })
    public ResponseEntity<UserDto> addUser(@ApiParam("User details") @Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
    }
}
