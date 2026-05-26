package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateAuthorUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByEmailPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateAuthorPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateAuthorCommand;
import com.jcaa.usersmanagement.application.service.mapper.AuthorApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AuthorAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.AuthorNotFoundException;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateAuthorService implements UpdateAuthorUseCase {

    private final UpdateAuthorPort updateAuthorPort;
    private final GetAuthorByIdPort getAuthorByIdPort;
    private final GetAuthorByEmailPort getAuthorByEmailPort;
    private final Validator validator;

    @Override
    public AuthorModel execute(final UpdateAuthorCommand command) {
        validateCommand(command);

        final AuthorId authorId = new AuthorId(command.id());
        findExistingAuthorOrFail(authorId);

        final AuthorEmail newEmail = new AuthorEmail(command.email());
        ensureEmailIsNotTakenByAnotherAuthor(newEmail, authorId);

        final AuthorModel authorToUpdate = AuthorApplicationMapper.fromUpdateCommandToModel(command);
        return updateAuthorPort.update(authorToUpdate);
    }

    private void validateCommand(final UpdateAuthorCommand command) {
        final Set<ConstraintViolation<UpdateAuthorCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void findExistingAuthorOrFail(final AuthorId authorId) {
        getAuthorByIdPort
                .getById(authorId)
                .orElseThrow(() -> AuthorNotFoundException.becauseIdWasNotFound(authorId.value()));
    }

    private void ensureEmailIsNotTakenByAnotherAuthor(final AuthorEmail newEmail, final AuthorId ownerId) {
        getAuthorByEmailPort
                .getByEmail(newEmail)
                .ifPresent(
                        found -> {
                            if (!found.getId().equals(ownerId)) {
                                throw AuthorAlreadyExistsException.becauseEmailAlreadyExists(newEmail.value());
                            }
                        });
    }
}