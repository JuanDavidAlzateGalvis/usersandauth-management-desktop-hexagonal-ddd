package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.*;
import com.jcaa.usersmanagement.application.service.dto.command.*;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByEmailQuery;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByIdQuery;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByCountryQuery;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByNameQuery;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByWorkCenterQuery;
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

    private final GetAuthorByEmailUseCase getAuthorByEmailUseCase;
    private final GetAuthorsByNameUseCase getAuthorsByNameUseCase;
    private final GetAuthorsByWorkCenterUseCase getAuthorsByWorkCenterUseCase;
    private final GetAuthorsByCountryUseCase getAuthorsByCountryUseCase;

    public AuthorResponse createAuthor(final CreateAuthorCommand command) {
        return AuthorDesktopMapper.fromModelToResponse(
                createAuthorUseCase.execute(command));
    }

    public AuthorResponse updateAuthor(final UpdateAuthorCommand command) {
        return AuthorDesktopMapper.fromModelToResponse(
                updateAuthorUseCase.execute(command));
    }

    public void deleteAuthor(final String id) {
        deleteAuthorUseCase.execute(new DeleteAuthorCommand(id));
    }

    public AuthorResponse getAuthorById(final String id) {
        return AuthorDesktopMapper.fromModelToResponse(
                getAuthorByIdUseCase.execute(new GetAuthorByIdQuery(id)));
    }

    public AuthorResponse getAuthorByEmail(final String email) {
        return AuthorDesktopMapper.fromModelToResponse(
                getAuthorByEmailUseCase.execute(
                        new GetAuthorByEmailQuery(email)));
    }

    public List<AuthorResponse> getAuthorsByName(final String name) {
        return AuthorDesktopMapper.fromModelListToResponseList(
                getAuthorsByNameUseCase.execute(
                        new GetAuthorsByNameQuery(name)));
    }

    public List<AuthorResponse> getAuthorsByWorkCenter(final String workCenter) {
        return AuthorDesktopMapper.fromModelListToResponseList(
                getAuthorsByWorkCenterUseCase.execute(
                        new GetAuthorsByWorkCenterQuery(workCenter)));
    }

    public List<AuthorResponse> getAuthorsByCountry(final String country) {
        return AuthorDesktopMapper.fromModelListToResponseList(
                getAuthorsByCountryUseCase.execute(
                        new GetAuthorsByCountryQuery(country)));
    }

    public List<AuthorResponse> getAllAuthors() {
        return AuthorDesktopMapper.fromModelListToResponseList(
                getAllAuthorsUseCase.execute());
    }
}