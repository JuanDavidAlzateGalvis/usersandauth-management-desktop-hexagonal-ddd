package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.AuthorResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorDesktopMapper {
    public AuthorResponse fromModelToResponse(final AuthorModel model) {
        return new AuthorResponse(
                model.getId().value(),
                model.getFullName().value(),
                model.getWorkCenter().value(),
                model.getEmail().value());
    }

    public List<AuthorResponse> fromModelListToResponseList(final List<AuthorModel> models) {
        return models.stream().map(AuthorDesktopMapper::fromModelToResponse).toList();
    }
}