package ru.ural.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ural.dto.UserDto;
import ru.ural.dto.UserRequest;

@RequestMapping("/api/users")
@Tag(name = "UsersController", description = "Контроллер для работы с пользователями")
public interface UsersApi {

    @PostMapping
    ResponseEntity<UserDto> create(@RequestBody UserRequest userRequest);

    @GetMapping("/{uuid}")
    ResponseEntity<UserDto> getByUuid(@PathVariable String uuid);

    @PutMapping("/{uuid}")
    ResponseEntity<UserDto> update(@PathVariable String uuid, @RequestBody UserRequest userRequest);

}
