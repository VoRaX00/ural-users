package ural.ru.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    private UUID uuid;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String patronymic;

    @Column(unique = true)
    private String phoneNumber;

}
