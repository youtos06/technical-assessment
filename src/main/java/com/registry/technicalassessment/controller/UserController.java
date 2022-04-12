package com.registry.technicalassessment.controller;

import com.registry.technicalassessment.annotation.LogExecutionTime;
import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.holder.ApiPath;
import com.registry.technicalassessment.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    @ApiOperation(value = "Retrieve all users or users with specific name")
    public ResponseEntity<List<UserDto>> getUsers(@ApiParam("Filter users by name") @RequestParam(required = false) String name) {
        List<UserDto> users;
        if (name != null) {
            users = userService.retrieveUserByName(name);
        } else {
            users = userService.retrieveAllUsers();
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.GET,
            value = ApiPath.USER_BY_ID,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    @ApiOperation(value = "Retrieve user by Id")
    public ResponseEntity<UserDto> getUserById(@PathVariable long id) {
        UserDto user = userService.retrieveUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @LogExecutionTime
    @ApiOperation(value = "Register a new user")
    public ResponseEntity<UserDto> addUser(@Valid @RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.saveUser(userDto), HttpStatus.CREATED);
    }
}
