package com.jcaa.usersmanagement.application.port.out;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import java.util.Optional;

public interface GetAuthorByIdPort {
    Optional<AuthorModel> getById(AuthorId id);
}