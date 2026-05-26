package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllAuthorsUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllAuthorsPort;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAllAuthorsService implements GetAllAuthorsUseCase {

    private final GetAllAuthorsPort getAllAuthorsPort;

    @Override
    public List<AuthorModel> execute() {
        return getAllAuthorsPort.getAll();
    }
}