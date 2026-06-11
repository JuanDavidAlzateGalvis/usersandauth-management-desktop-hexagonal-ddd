package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetAuthorsByNameQuery(
        @NotBlank(message = "name must not be blank")
        String name
) {}