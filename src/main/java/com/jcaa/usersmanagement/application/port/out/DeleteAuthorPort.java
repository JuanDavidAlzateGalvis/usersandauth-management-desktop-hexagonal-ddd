package com.jcaa.usersmanagement.application.port.out;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;

public interface DeleteAuthorPort {
    void delete(AuthorId id);
}