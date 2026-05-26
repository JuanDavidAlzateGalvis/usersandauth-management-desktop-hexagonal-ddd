package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAuthorCommand(
        @NotBlank(message = "id must not be blank") String id,
        @NotBlank(message = "full name must not be blank")
        @Size(min = 3, message = "full name must have at least 3 characters") String fullName,
        @NotBlank(message = "work center must not be blank") String workCenter,
        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid email address") String email
) {}