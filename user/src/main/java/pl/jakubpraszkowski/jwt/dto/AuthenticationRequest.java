package pl.jakubpraszkowski.jwt.dto;

public record AuthenticationRequest(String login, String password) {
}
