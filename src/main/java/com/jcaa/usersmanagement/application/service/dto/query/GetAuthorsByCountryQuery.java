package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetAuthorsByCountryQuery(
        @NotBlank(message = "country must not be blank")
        String country
) {}
