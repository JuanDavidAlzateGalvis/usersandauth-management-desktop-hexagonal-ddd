package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateAuthorCommand;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateAuthorUseCase {
    AuthorModel execute(@NotNull @Valid UpdateAuthorCommand command);
}