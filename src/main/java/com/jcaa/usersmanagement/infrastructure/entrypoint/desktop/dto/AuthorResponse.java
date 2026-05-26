package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record AuthorResponse(String id, String fullName, String workCenter, String email) {
    @Override
    public String toString() {
        return String.format("ID: %s | Name: %s | Center: %s | Email: %s", id, fullName, workCenter, email);
    }
}