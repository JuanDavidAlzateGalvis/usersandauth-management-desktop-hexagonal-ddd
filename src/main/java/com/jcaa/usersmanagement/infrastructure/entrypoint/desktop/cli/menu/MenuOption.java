package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu;

import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuOption {

    // ── Usuarios (originales) ──────────────────────────────────────────────
    LIST_USERS(1,  "List all users"),
    FIND_USER(2,   "Find user by ID"),
    CREATE_USER(3, "Create user"),
    UPDATE_USER(4, "Update user"),
    DELETE_USER(5, "Delete user"),
    LOGIN(6,       "Login"),

    // ── Autores – CRUDL (Unidad 3) ────────────────────────────────────────
    CREATE_AUTHOR(7,  "Create Author"),
    UPDATE_AUTHOR(8,  "Update Author"),
    FIND_AUTHOR(9,    "Find Author By ID"),
    LIST_AUTHORS(10,  "List All Authors"),
    DELETE_AUTHOR(11, "Delete Author"),

    // ── Autores – 5 Consultas (Unidad 4 – CEA 21) ─────────────────────────
    // Consulta 1 – "Mostrar los autores por dirección de correo electrónico"
    FIND_AUTHOR_BY_EMAIL(12,      "Find Author By Email"),
    // Consulta 2 – "Listar los autores por nombre"
    FIND_AUTHORS_BY_NAME(13,      "Find Authors By Name"),
    // Consulta 3 – "Obtener los autores por afiliación universitaria"
    FIND_AUTHORS_BY_WORK_CENTER(14, "Find Authors By Work Center"),
    // Consulta 4 – "Consultar los autores por país de origen"
    FIND_AUTHORS_BY_COUNTRY(15,   "Find Authors By Country"),

    EXIT(0, "Exit");

    private final int number;
    private final String description;

    public static Optional<MenuOption> fromNumber(final int number) {
        for (final MenuOption option : values()) {
            if (option.number == number) {
                return Optional.of(option);
            }
        }
        return Optional.empty();
    }
}