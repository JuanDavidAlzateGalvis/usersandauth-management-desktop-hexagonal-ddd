package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteAuthorUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteAuthorPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteAuthorCommand;
import com.jcaa.usersmanagement.application.service.mapper.AuthorApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.AuthorNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteAuthorService implements DeleteAuthorUseCase {

    private final DeleteAuthorPort deleteAuthorPort;
    private final GetAuthorByIdPort getAuthorByIdPort;
    private final Validator validator;

    @Override
    public void execute(final DeleteAuthorCommand command) {
        validateCommand(command);

        final AuthorId authorId = AuthorApplicationMapper.fromDeleteCommandToAuthorId(command);
        ensureAuthorExists(authorId);
        deleteAuthorPort.delete(authorId);
    }

    private void validateCommand(final DeleteAuthorCommand command) {
        final Set<ConstraintViolation<DeleteAuthorCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureAuthorExists(final AuthorId authorId) {
        getAuthorByIdPort
                .getById(authorId)
                .orElseThrow(() -> AuthorNotFoundException.becauseIdWasNotFound(authorId.value()));
    }
}