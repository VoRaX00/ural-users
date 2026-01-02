package ural.ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ural.ru.entities.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
