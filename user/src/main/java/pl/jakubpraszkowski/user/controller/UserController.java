package pl.jakubpraszkowski.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.jakubpraszkowski.user.dto.RegisterRequest;
import pl.jakubpraszkowski.user.dto.UserResponse;
import pl.jakubpraszkowski.user.service.UserService;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserController {
    private final UserService userService;

    @GetMapping("/{email}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody RegisterRequest registerRequest) {
        return userService.registerUser(registerRequest);
    }
}
