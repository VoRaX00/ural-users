package ru.ural.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ural.entities.User;
import ru.ural.exceptions.ConflictException;
import ru.ural.exceptions.NotFoundException;
import ru.ural.mappers.UserMapper;
import ru.ural.models.UserModel;
import ru.ural.repositories.UserRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private static final String ERROR_USER_EXISTS = "Пользователь с таким email или номером телефона уже существует";

    private static final String USER_NOT_FOUND = "Пользователь с uuid: %s не найден";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserModel create(@NonNull UserModel userModel) {
        validateUser(userModel);

        User newUser = userMapper.toEntity(userModel);
        User savedUser = userRepository.save(newUser);
        return userMapper.toModel(savedUser);
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
