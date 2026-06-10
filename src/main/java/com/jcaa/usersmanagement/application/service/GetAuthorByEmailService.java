package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAuthorByEmailUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByEmailPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByEmailQuery;
import com.jcaa.usersmanagement.domain.exception.AuthorNotFoundException;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAuthorByEmailService implements GetAuthorByEmailUseCase {

    private final GetAuthorByEmailPort getAuthorByEmailPort;
    private final Validator validator;

    @Override
    public AuthorModel execute(final GetAuthorByEmailQuery query) {
        validateQuery(query);

        final AuthorEmail email = new AuthorEmail(query.email());

        return getAuthorByEmailPort
                .getByEmail(email)
                .orElseThrow(() ->
                        AuthorNotFoundException.becauseEmailWasNotFound(email.value()));
    }

    private void validateQuery(final GetAuthorByEmailQuery query) {
        final Set<ConstraintViolation<GetAuthorByEmailQuery>> violations = validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}