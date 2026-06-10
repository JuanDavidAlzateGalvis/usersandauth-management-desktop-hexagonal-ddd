package com.jcaa.usersmanagement.application.service.dto.query;

import jakarta.validation.constraints.NotBlank;

public record GetAuthorsByWorkCenterQuery(
        @NotBlank(message = "workCenter must not be blank")
        String workCenter
) {}