package com.jcaa.usersmanagement.application.port.out;
import com.jcaa.usersmanagement.domain.model.AuthorModel;

public interface UpdateAuthorPort {
    AuthorModel update(AuthorModel author);
}