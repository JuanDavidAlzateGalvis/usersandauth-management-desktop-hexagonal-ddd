package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAuthorsByCountryUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByCountryPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByCountryQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAuthorsByCountryService implements GetAuthorsByCountryUseCase {

    private final GetAuthorsByCountryPort getAuthorsByCountryPort;
    private final Validator validator;

    @Override
    public List<AuthorModel> execute(final GetAuthorsByCountryQuery query) {
        validateQuery(query);
        return getAuthorsByCountryPort.getByCountry(query.country());
    }

    private void validateQuery(final GetAuthorsByCountryQuery query) {
        final Set<ConstraintViolation<GetAuthorsByCountryQuery>> violations = validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}