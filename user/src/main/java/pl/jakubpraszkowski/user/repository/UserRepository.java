package pl.jakubpraszkowski.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import pl.jakubpraszkowski.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String login);
    Optional<User> findByEmailOrUsername(@Param("login") String login);
}
