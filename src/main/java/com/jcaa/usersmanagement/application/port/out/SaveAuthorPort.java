package com.jcaa.usersmanagement.application.port.out;
import com.jcaa.usersmanagement.domain.model.AuthorModel;

public interface SaveAuthorPort {
    AuthorModel save(AuthorModel author);
}