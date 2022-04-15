package com.registry.technicalassessment.mapper;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
