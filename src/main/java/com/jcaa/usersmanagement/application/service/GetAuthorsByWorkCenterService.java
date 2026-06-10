package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAuthorsByNameUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByNamePort;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByNameQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAuthorsByNameService implements GetAuthorsByNameUseCase {

    private final GetAuthorsByNamePort getAuthorsByNamePort;
    private final Validator validator;

    @Override
    public List<AuthorModel> execute(final GetAuthorsByNameQuery query) {
        validateQuery(query);
        return getAuthorsByNamePort.getByName(query.name());
    }

    private void validateQuery(final GetAuthorsByNameQuery query) {
        final Set<ConstraintViolation<GetAuthorsByNameQuery>> violations = validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}