package com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity;

public record AuthorEntity(
        String id,
        String fullName,
        String workCenter,
        String email,
        String createdAt,
        String updatedAt) {}