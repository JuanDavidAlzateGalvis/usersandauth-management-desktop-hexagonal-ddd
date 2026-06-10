package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record GetAuthorByEmailQuery(
        @NotBlank(message = "email must not be blank")
        @Email(message = "email format is invalid")
        String email
) {}