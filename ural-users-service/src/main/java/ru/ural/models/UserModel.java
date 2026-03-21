package ru.ural.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    private UUID uuid;

    private String email;

    private String firstName;

    private String lastName;

    private String patronymic;

    private String phoneNumber;

}
