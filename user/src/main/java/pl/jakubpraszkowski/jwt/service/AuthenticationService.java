package pl.jakubpraszkowski.jwt.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.jakubpraszkowski.jwt.dto.AuthenticationRequest;
import pl.jakubpraszkowski.jwt.dto.AuthenticationResponse;
import pl.jakubpraszkowski.jwt.exception.UserAlreadyExistsException;
import pl.jakubpraszkowski.user.dto.RegisterRequest;
import pl.jakubpraszkowski.user.entity.Roles;
import pl.jakubpraszkowski.user.entity.User;
import pl.jakubpraszkowski.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        checkIfUserExists(request.email(), request.username());

        User user = createUser(request);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticateUser(request);
        User user = findUserByLogin(request.login());
        String jwtToken = jwtService.generateToken(user);

        return new AuthenticationResponse(jwtToken);
    }

    private User createUser(RegisterRequest request) {
        return User.builder().username(request.username())
                .password(passwordEncoder.encode(request.password())).email(request.email())
                .role(Roles.USER).build();
    }

    private void authenticateUser(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.login(), request.password()));
    }

    private User findUserByLogin(String login) {
        return userRepository.findByEmailOrUsername(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + login));
    }

    private void checkIfUserExists(String email, String username) {
        if (userRepository.findByEmailOrUsername(email).isPresent() || userRepository.findByEmailOrUsername(username).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + email + " or username " + username + " already exists");
        }
    }
}
