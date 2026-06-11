package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAuthorsByWorkCenterUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByWorkCenterPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorsByWorkCenterQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class GetAuthorsByWorkCenterService implements GetAuthorsByWorkCenterUseCase {

    private final GetAuthorsByWorkCenterPort getAuthorsByWorkCenterPort;
    private final Validator validator;

    @Override
    public List<AuthorModel> execute(final GetAuthorsByWorkCenterQuery query) {
        validateQuery(query);
        return getAuthorsByWorkCenterPort.getByWorkCenter(query.workCenter());
    }

    private void validateQuery(final GetAuthorsByWorkCenterQuery query) {
        final Set<ConstraintViolation<GetAuthorsByWorkCenterQuery>> violations =
                validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}