package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateAuthorUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByEmailPort;
import com.jcaa.usersmanagement.application.port.out.SaveAuthorPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateAuthorCommand;
import com.jcaa.usersmanagement.application.service.mapper.AuthorApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AuthorAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateAuthorService implements CreateAuthorUseCase {

    private final SaveAuthorPort saveAuthorPort;
    private final GetAuthorByEmailPort getAuthorByEmailPort;
    private final Validator validator;

    @Override
    public AuthorModel execute(final CreateAuthorCommand command) {
        validateCommand(command);

        final AuthorEmail email = new AuthorEmail(command.email());
        ensureEmailIsNotTaken(email);

        final AuthorModel authorToSave = AuthorApplicationMapper.fromCreateCommandToModel(command);
        return saveAuthorPort.save(authorToSave);
    }

    private void validateCommand(final CreateAuthorCommand command) {
        final Set<ConstraintViolation<CreateAuthorCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureEmailIsNotTaken(final AuthorEmail email) {
        getAuthorByEmailPort
                .getByEmail(email)
                .ifPresent(
                        found -> {
                            throw AuthorAlreadyExistsException.becauseEmailAlreadyExists(email.value());
                        });
    }
}