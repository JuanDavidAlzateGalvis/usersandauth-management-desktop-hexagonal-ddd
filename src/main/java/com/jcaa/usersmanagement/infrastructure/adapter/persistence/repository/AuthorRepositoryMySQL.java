package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteAuthorPort;
import com.jcaa.usersmanagement.application.port.out.GetAllAuthorsPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByEmailPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveAuthorPort;
import com.jcaa.usersmanagement.application.port.out.UpdateAuthorPort;
import com.jcaa.usersmanagement.domain.exception.AuthorNotFoundException;
import com.jcaa.usersmanagement.domain.model.AuthorModel;
import com.jcaa.usersmanagement.domain.valueobject.AuthorEmail;
import com.jcaa.usersmanagement.domain.valueobject.AuthorId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.dto.AuthorPersistenceDto;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.AuthorPersistenceMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByNamePort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByWorkCenterPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByCountryPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByNamePort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByWorkCenterPort;
import com.jcaa.usersmanagement.application.port.out.GetAuthorsByCountryPort;

@Log
@RequiredArgsConstructor
public final class AuthorRepositoryMySQL
        implements SaveAuthorPort,
        UpdateAuthorPort,
        GetAuthorByIdPort,
        GetAuthorByEmailPort,
        GetAllAuthorsPort,
        DeleteAuthorPort,
        GetAuthorsByNamePort,
        GetAuthorsByWorkCenterPort,
        GetAuthorsByCountryPort {

    private static final String SQL_INSERT =
            "INSERT INTO authors (id, full_name, work_center, email, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())";

    private static final String SQL_UPDATE =
            "UPDATE authors SET full_name = ?, work_center = ?, email = ?, updated_at = NOW() WHERE id = ?";

    private static final String SQL_SELECT_BY_ID =
            "SELECT id, full_name, work_center, email, created_at, updated_at FROM authors WHERE id = ? LIMIT 1";

    private static final String SQL_SELECT_BY_EMAIL =
        
            "SELECT id, full_name, work_center, email, created_at, updated_at FROM authors WHERE email = ? LIMIT 1";

    private static final String SQL_SELECT_ALL =
            "SELECT id, full_name, work_center, email, created_at, updated_at FROM authors ORDER BY full_name ASC";
    private static final String SQL_SELECT_BY_NAME =
        """
        SELECT id, full_name, work_center, email, created_at, updated_at
        FROM authors
        WHERE LOWER(full_name) LIKE LOWER(?)
        ORDER BY full_name ASC
        """;

private static final String SQL_SELECT_BY_WORK_CENTER =
        """
        SELECT id, full_name, work_center, email, created_at, updated_at
        FROM authors
        WHERE LOWER(work_center) LIKE LOWER(?)
        ORDER BY full_name ASC
        """;

private static final String SQL_SELECT_BY_COUNTRY =
        """
        SELECT id, full_name, work_center, email, created_at, updated_at
        FROM authors
        WHERE LOWER(work_center) LIKE LOWER(?)
        ORDER BY full_name ASC
        """;

    private static final String SQL_DELETE = "DELETE FROM authors WHERE id = ?";

    private final Connection connection;

    @Override
    public AuthorModel save(final AuthorModel author) {
        final AuthorPersistenceDto dto = AuthorPersistenceMapper.fromModelToDto(author);
        executeSave(dto);
        return findByIdOrFail(author.getId());
    }

    @Override
    public AuthorModel update(final AuthorModel author) {
        final AuthorPersistenceDto dto = AuthorPersistenceMapper.fromModelToDto(author);
        executeUpdate(dto);
        return findByIdOrFail(author.getId());
    }

    @Override
    public Optional<AuthorModel> getById(final AuthorId authorId) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setString(1, authorId.value());
            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(AuthorPersistenceMapper.fromResultSetToModel(resultSet));
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindByIdFailed(authorId.value(), exception);
        }
    }

    @Override
    public Optional<AuthorModel> getByEmail(final AuthorEmail email) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL)) {
            statement.setString(1, email.value());
            final ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return Optional.empty();
            }
            return Optional.of(AuthorPersistenceMapper.fromResultSetToModel(resultSet));
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindByEmailFailed(email.value(), exception);
        }
    }

    @Override
    public List<AuthorModel> getAll() {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            final ResultSet resultSet = statement.executeQuery();
            return AuthorPersistenceMapper.fromResultSetToModelList(resultSet);
        } catch (final SQLException exception) {
            throw PersistenceException.becauseFindAllFailed(exception);
        }
    }

    @Override
    public void delete(final AuthorId authorId) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
            statement.setString(1, authorId.value());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseDeleteFailed(authorId.value(), exception);
        }
    }

    private void executeSave(final AuthorPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
            statement.setString(1, dto.id());
            statement.setString(2, dto.fullName());
            statement.setString(3, dto.workCenter());
            statement.setString(4, dto.email());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseSaveFailed(dto.id(), exception);
        }
    }

    private void executeUpdate(final AuthorPersistenceDto dto) {
        try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, dto.fullName());
            statement.setString(2, dto.workCenter());
            statement.setString(3, dto.email());
            statement.setString(4, dto.id());
            statement.executeUpdate();
        } catch (final SQLException exception) {
            throw PersistenceException.becauseUpdateFailed(dto.id(), exception);
        }
    }

   private AuthorModel findByIdOrFail(final AuthorId authorId) {
    return getById(authorId)
            .orElseThrow(() -> AuthorNotFoundException.becauseIdWasNotFound(authorId.value()));
}

@Override
public List<AuthorModel> getByName(final String name) {

    try (final PreparedStatement statement =
                 connection.prepareStatement(SQL_SELECT_BY_NAME)) {

        statement.setString(1, "%" + name + "%");

        final ResultSet resultSet = statement.executeQuery();

        return AuthorPersistenceMapper.fromResultSetToModelList(resultSet);

    } catch (final SQLException exception) {

        throw PersistenceException.becauseFindAllFailed(exception);
    }
}

@Override
public List<AuthorModel> getByWorkCenter(final String workCenter) {

    try (final PreparedStatement statement =
                 connection.prepareStatement(SQL_SELECT_BY_WORK_CENTER)) {

        statement.setString(1, "%" + workCenter + "%");

        final ResultSet resultSet = statement.executeQuery();

        return AuthorPersistenceMapper.fromResultSetToModelList(resultSet);

    } catch (final SQLException exception) {

        throw PersistenceException.becauseFindAllFailed(exception);
    }
}

@Override
public List<AuthorModel> getByCountry(final String country) {

    try (final PreparedStatement statement =
                 connection.prepareStatement(SQL_SELECT_BY_COUNTRY)) {

        statement.setString(1, "%" + country + "%");

        final ResultSet resultSet = statement.executeQuery();

        return AuthorPersistenceMapper.fromResultSetToModelList(resultSet);

    } catch (final SQLException exception) {

        throw PersistenceException.becauseFindAllFailed(exception);
    }
}
}