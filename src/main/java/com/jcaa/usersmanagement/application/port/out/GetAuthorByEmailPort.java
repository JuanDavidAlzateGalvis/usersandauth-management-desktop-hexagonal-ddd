package com.jcaa.usersmanagement.application.port.out;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import java.util.Optional;

public interface GetAuthorByEmailPort {
    Optional<AuthorModel> getByEmail(AuthorEmail email);
}