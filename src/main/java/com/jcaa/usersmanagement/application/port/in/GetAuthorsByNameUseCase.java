package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByNameQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public interface GetAuthorsByNameUseCase {

    List<AuthorModel> execute(
            @NotNull @Valid GetAuthorsByNameQuery query
    );

}
