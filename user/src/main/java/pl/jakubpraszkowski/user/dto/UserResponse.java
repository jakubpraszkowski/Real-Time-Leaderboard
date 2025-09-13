package pl.jakubpraszkowski.user.dto;

import pl.jakubpraszkowski.user.entity.Roles;

public record UserResponse(Long id, String username, String firstName, String lastName, String email, Roles role) {
}
