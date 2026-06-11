package com.jcaa.usersmanagement.domain.exception;

public final class AuthorNotFoundException extends DomainException {

    private AuthorNotFoundException(final String message) {
        super(message);
    }

    public static AuthorNotFoundException becauseIdWasNotFound(final String id) {
        return new AuthorNotFoundException(
                String.format("The author with id '%s' was not found.", id));
    }

    public static AuthorNotFoundException becauseEmailWasNotFound(final String email) {
        return new AuthorNotFoundException(
                String.format("The author with email '%s' was not found.", email));
    }
}