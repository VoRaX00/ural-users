package ru.ural.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.ural.api.UsersApi;
import ru.ural.auth.dto.AuthDto;
import ru.ural.dto.UserDto;
import ru.ural.dto.UserRegistration;
import ru.ural.dto.UserRequest;
import ru.ural.mappers.UserMapper;
import ru.ural.models.RegistrationModel;
import ru.ural.models.UserModel;
import ru.ural.services.UserService;

@RestController
@RequiredArgsConstructor
public class UserController implements UsersApi {

    private final UserService userService;

    private final UserMapper userMapper;

    @Override
    public ResponseEntity<AuthDto> registration(UserRegistration userRegistration) {
        RegistrationModel requestModel = userMapper.toModel(userRegistration);
        return ResponseEntity.ok(userService.create(requestModel));
    }

    @Override
    public ResponseEntity<UserDto> getByUuid(String uuid) {
        UserModel model = userService.getByUuid(uuid);
        return ResponseEntity.ok(userMapper.toDto(model));
    }

    @Override
    public ResponseEntity<UserDto> update(String uuid, UserRequest userRequest) {
        UserModel requestModel = userMapper.toModel(userRequest);
        UserModel updatedUser = userService.updateUser(uuid, requestModel);
        return ResponseEntity.ok(userMapper.toDto(updatedUser));
    }

}
