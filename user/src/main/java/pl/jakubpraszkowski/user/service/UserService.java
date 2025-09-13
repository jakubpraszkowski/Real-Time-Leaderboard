package pl.jakubpraszkowski.user.service;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpraszkowski.user.dto.RegisterRequest;
import pl.jakubpraszkowski.user.dto.UserResponse;
import pl.jakubpraszkowski.user.entity.Roles;
import pl.jakubpraszkowski.user.entity.User;
import pl.jakubpraszkowski.user.exception.UserNotFoundException;
import pl.jakubpraszkowski.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Faker faker;

    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User " + email + " not found in Database"));

        return toUserResponse(user);
    }

    @Transactional
    public UserResponse registerUser(RegisterRequest request) {
        String firstName = getOrRandom(request.firstName(), faker.name().firstName());
        String lastName  = getOrRandom(request.lastName(), faker.name().lastName());

        User user = toUser(request, firstName, lastName);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return toUserResponse(user);
    }

    private String getOrRandom(String provided, String randomValue) {
        return (provided == null || provided.isBlank()) ? randomValue : provided;
    }

    private User toUser(RegisterRequest userRequest, String firstName, String lastName) {
        return User.builder().username(userRequest.username()).password(userRequest.password())
                .email(userRequest.email()).role(Roles.USER).firstName(firstName).lastName(lastName)
                .build();
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), null, null, user.getEmail(), user.getRole());
    }
}
