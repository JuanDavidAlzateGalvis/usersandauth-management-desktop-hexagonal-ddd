package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateAuthorCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteAuthorCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateAuthorCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetAuthorByIdQuery;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import com.jcaa.usersmanagement.domain.valueobject.AuthorName;
import com.jcaa.usersmanagement.domain.valueobject.WorkCenter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorApplicationMapper {

    public AuthorModel fromCreateCommandToModel(final CreateAuthorCommand command) {
        return AuthorModel.create(
                new AuthorId(command.id()),
                new AuthorName(command.fullName()),
                new WorkCenter(command.workCenter()),
                new AuthorEmail(command.email()));
    }

    public AuthorModel fromUpdateCommandToModel(final UpdateAuthorCommand command) {
        return new AuthorModel(
                new AuthorId(command.id()),
                new AuthorName(command.fullName()),
                new WorkCenter(command.workCenter()),
                new AuthorEmail(command.email()));
    }

    public AuthorId fromGetAuthorByIdQueryToAuthorId(final GetAuthorByIdQuery query) {
        return new AuthorId(query.id());
    }

    public AuthorId fromDeleteCommandToAuthorId(final DeleteAuthorCommand command) {
        return new AuthorId(command.id());
    }
}