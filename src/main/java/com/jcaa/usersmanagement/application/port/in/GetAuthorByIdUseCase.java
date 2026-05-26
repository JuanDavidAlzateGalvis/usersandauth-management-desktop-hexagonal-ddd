package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByIdQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetAuthorByIdUseCase {
    AuthorModel execute(@NotNull @Valid GetAuthorByIdQuery query);
}