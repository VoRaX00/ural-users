package ru.ural.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ural.auth.dto.AuthDto;
import ru.ural.auth.dto.UserDto;
import ru.ural.users.entities.User;
import ru.ural.exceptions.ConflictException;
import ru.ural.exceptions.NotFoundException;
import ru.ural.users.mappers.UserMapper;
import ru.ural.users.models.RegistrationModel;
import ru.ural.users.models.UserModel;
import ru.ural.users.repositories.UserRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ERROR_USER_EXISTS = "Пользователь с таким email или номером телефона уже существует";

    private static final String USER_NOT_FOUND = "Пользователь с uuid: %s не найден";

    private final AuthSender authSender;

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional
    public AuthDto create(@NonNull RegistrationModel model) {
        validateUser(model);

        User newUser = userMapper.toEntity(model);
        User savedUser = userRepository.save(newUser);
        return sendRegistration(model, savedUser.getUuid());
    }

    private AuthDto sendRegistration(RegistrationModel user, UUID uuid) {
        UserDto userDto = userMapper.toAuthUserDto(user);
        userDto.setUuid(uuid);

        return authSender.registration(userDto);
    }

    @Transactional
    public UserModel updateUser(@NonNull String uuid, @NonNull UserModel userModel) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NotFoundException(String.format(
                        USER_NOT_FOUND, uuid
                )));

        validateUser(userModel);
        userMapper.mapModelToEntity(user, userModel);
        return userMapper.toModel(user);
    }

    private void validateUser(UserModel userModel) {
        boolean existsUser = userRepository.existsByEmailOrPhoneNumber(
                userModel.getEmail(),
                userModel.getPhoneNumber()
        );

        if (existsUser) {
            throw new ConflictException(ERROR_USER_EXISTS);
        }
    }

    public UserModel getByUuid(String uuid) {
        User user = userRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new NotFoundException(String.format(
                        USER_NOT_FOUND, uuid
                )));

        return userMapper.toModel(user);
    }

}
