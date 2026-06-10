package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByEmailQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetAuthorByEmailUseCase {

    AuthorModel execute(
            @NotNull @Valid GetAuthorByEmailQuery query
    );

}