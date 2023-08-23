package com.ipdec.reportsapi.domain.model.user;

public record RegisterDTO(String login, String password, UserRole role) {
}
