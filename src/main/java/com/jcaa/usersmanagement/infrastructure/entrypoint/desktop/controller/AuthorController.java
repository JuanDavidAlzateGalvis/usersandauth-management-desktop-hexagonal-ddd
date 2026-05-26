package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.*;
import com.jcaa.usersmanagement.application.service.dto.command.*;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByIdQuery;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.AuthorResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.AuthorDesktopMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class AuthorController {
    private final CreateAuthorUseCase createAuthorUseCase;
    private final UpdateAuthorUseCase updateAuthorUseCase;
    private final DeleteAuthorUseCase deleteAuthorUseCase;
    private final GetAuthorByIdUseCase getAuthorByIdUseCase;
    private final GetAllAuthorsUseCase getAllAuthorsUseCase;

    public AuthorResponse createAuthor(final CreateAuthorCommand command) {
        return AuthorDesktopMapper.fromModelToResponse(createAuthorUseCase.execute(command));
    }

    public AuthorResponse updateAuthor(final UpdateAuthorCommand command) {
        return AuthorDesktopMapper.fromModelToResponse(updateAuthorUseCase.execute(command));
    }

    public void deleteAuthor(final String id) {
        deleteAuthorUseCase.execute(new DeleteAuthorCommand(id));
    }

    public AuthorResponse getAuthorById(final String id) {
        return AuthorDesktopMapper.fromModelToResponse(getAuthorByIdUseCase.execute(new GetAuthorByIdQuery(id)));
    }

    public List<AuthorResponse> getAllAuthors() {
        return AuthorDesktopMapper.fromModelListToResponseList(getAllAuthorsUseCase.execute());
    }
}