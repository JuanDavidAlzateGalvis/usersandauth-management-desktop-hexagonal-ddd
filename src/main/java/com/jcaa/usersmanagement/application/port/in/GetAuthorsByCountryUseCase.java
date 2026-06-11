package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByCountryQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public interface GetAuthorsByCountryUseCase {

    List<AuthorModel> execute(
            @NotNull @Valid GetAuthorsByCountryQuery query
    );

}