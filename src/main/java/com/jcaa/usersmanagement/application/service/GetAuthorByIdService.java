package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAuthorByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.AuthorApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AuthorNotFoundException;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAuthorByIdService implements GetAuthorByIdUseCase {

    private final GetAuthorByIdPort getAuthorByIdPort;
    private final Validator validator;

    @Override
    public AuthorModel execute(final GetAuthorByIdQuery query) {
        validateQuery(query);

        final AuthorId authorId = AuthorApplicationMapper.fromGetAuthorByIdQueryToAuthorId(query);
        return getAuthorByIdPort
                .getById(authorId)
                .orElseThrow(() -> AuthorNotFoundException.becauseIdWasNotFound(authorId.value()));
    }

    private void validateQuery(final GetAuthorByIdQuery query) {
        final Set<ConstraintViolation<GetAuthorByIdQuery>> violations = validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}