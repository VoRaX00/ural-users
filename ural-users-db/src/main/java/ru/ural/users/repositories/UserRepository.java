package ru.ural.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.ural.users.entities.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query(value = """
        SELECT EXISTS(
            SELECT 1
            FROM users
            WHERE email = :email OR phone_number = :phoneNumber
        )
    """, nativeQuery = true)
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

}
