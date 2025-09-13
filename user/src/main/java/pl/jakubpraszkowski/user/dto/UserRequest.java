package pl.jakubpraszkowski.user.dto;

import pl.jakubpraszkowski.user.entity.Roles;

public record UserRequest(String username, String password, String email, Roles role) {
}
