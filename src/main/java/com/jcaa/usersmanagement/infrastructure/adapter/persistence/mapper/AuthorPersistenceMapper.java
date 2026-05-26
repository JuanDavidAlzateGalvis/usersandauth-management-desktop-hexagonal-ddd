package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import com.jcaa.usersmanagement.domain.valueobject.AuthorName;
import com.jcaa.usersmanagement.domain.valueobject.WorkCenter;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.AuthorPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.AuthorEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorPersistenceMapper {

    public AuthorPersistenceDto fromModelToDto(final AuthorModel author) {
        return new AuthorPersistenceDto(
                author.getId().value(),
                author.getFullName().value(),
                author.getWorkCenter().value(),
                author.getEmail().value(),
                null,
                null);
    }

    public AuthorEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
        return new AuthorEntity(
                resultSet.getString("id"),
                resultSet.getString("full_name"),
                resultSet.getString("work_center"),
                resultSet.getString("email"),
                resultSet.getString("created_at"),
                resultSet.getString("updated_at"));
    }

    public AuthorModel fromEntityToModel(final AuthorEntity entity) {
        return new AuthorModel(
                new AuthorId(entity.id()),
                new AuthorName(entity.fullName()),
                new WorkCenter(entity.workCenter()),
                new AuthorEmail(entity.email()));
    }

    public AuthorModel fromResultSetToModel(final ResultSet resultSet) throws SQLException {
        return fromEntityToModel(fromResultSetToEntity(resultSet));
    }

    public List<AuthorModel> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
        final List<AuthorModel> authors = new ArrayList<>();
        while (resultSet.next()) {
            authors.add(fromResultSetToModel(resultSet));
        }
        return authors;
    }
}