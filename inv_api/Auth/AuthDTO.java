package com.clinic.inv_api.Auth;

public class AuthDTO {
    public record AuthRequestDTO(String username, String password) {}
    public record AuthResponseDTO(String token) {}
}