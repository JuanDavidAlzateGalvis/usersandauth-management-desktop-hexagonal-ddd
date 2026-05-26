package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.domain.model.AuthorModel;
import java.util.List;

public interface GetAllAuthorsUseCase {
    List<AuthorModel> execute();
}