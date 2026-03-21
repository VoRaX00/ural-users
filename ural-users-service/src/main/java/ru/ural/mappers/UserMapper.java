package ru.ural.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.ural.dto.UserDto;
import ru.ural.dto.UserRequest;
import ru.ural.entities.User;
import ru.ural.models.UserModel;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    User toEntity(UserModel userModel);

    UserModel toModel(User user);

    @Mapping(target = "uuid", ignore = true)
    UserModel toModel(UserRequest userRequest);

    UserDto toDto(UserModel userModel);

    @Mapping(target = "uuid", ignore = true)
    void mapModelToEntity(@MappingTarget User user, UserModel userModel);

}
