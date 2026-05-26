package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.AuthorModel;
import java.util.List;

public interface GetAllAuthorsPort {
    List<AuthorModel> getAll();
}