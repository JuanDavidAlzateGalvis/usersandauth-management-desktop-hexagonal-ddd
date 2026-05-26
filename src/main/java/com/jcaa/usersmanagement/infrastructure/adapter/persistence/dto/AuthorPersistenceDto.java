package com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto;

public record AuthorPersistenceDto(
        String id,
        String fullName,
        String workCenter,
        String email,
        String createdAt,
        String updatedAt) {}