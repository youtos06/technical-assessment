package com.registry.technicalassessment.mapper;

import com.registry.technicalassessment.dto.UserDto;
import com.registry.technicalassessment.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "country.code" , source = "country")
    User userDtoToUser(UserDto userDto);

    @Mapping(source = "country.code" , target = "country")
    UserDto userToUserDto(User user);
}
