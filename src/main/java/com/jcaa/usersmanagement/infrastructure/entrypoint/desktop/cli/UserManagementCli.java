package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler.*;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.UserResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu.MenuOption;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.UserController;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UserManagementCli {

    private static final String BANNER =
            """
            ==========================================
                 Users & Authors Management System
            ==========================================""";

    private static final String MENU_BORDER =
            "  ==========================================";

    private final UserController   userController;
    private final AuthorController authorController;
    private final ConsoleIO        console;

    public void start() {
        console.println(BANNER);
        final UserResponsePrinter printer = new UserResponsePrinter(console);
        runLoop(buildHandlers(printer));
    }

    // ── Loop principal ────────────────────────────────────────────────────────

    private void runLoop(final Map<MenuOption, OperationHandler> handlers) {
        boolean running = true;
        while (running) {
            printMenu();
            final int choice = console.readInt("\n  Option: ");
            final Optional<MenuOption> option = MenuOption.fromNumber(choice);

            if (option.isEmpty()) {
                console.println("  Invalid option. Please try again.");
            } else if (option.get() == MenuOption.EXIT) {
                console.println("\n  Goodbye!\n");
                running = false;
            } else {
                executeHandler(handlers, option.get());
            }
        }
    }

    private void executeHandler(
            final Map<MenuOption, OperationHandler> handlers, final MenuOption option) {
        try {
            handlers.get(option).handle();
        } catch (final ConstraintViolationException ex) {
            console.println("  Validation errors:");
            ex.getConstraintViolations()
              .forEach(v -> console.println("    - " + v.getMessage()));
        } catch (final RuntimeException ex) {
            console.println("  Error: " + ex.getMessage());
        }
    }

    // ── Registro de handlers ──────────────────────────────────────────────────

    private Map<MenuOption, OperationHandler> buildHandlers(final UserResponsePrinter printer) {
        final Map<MenuOption, OperationHandler> handlers = new HashMap<>();

        // Users (Unidades 1–3, originales)
        handlers.put(MenuOption.LIST_USERS,  new ListUsersHandler(userController, printer));
        handlers.put(MenuOption.FIND_USER,   new FindUserByIdHandler(userController, console, printer));
        handlers.put(MenuOption.CREATE_USER, new CreateUserHandler(userController, console, printer));
        handlers.put(MenuOption.UPDATE_USER, new UpdateUserHandler(userController, console, printer));
        handlers.put(MenuOption.DELETE_USER, new DeleteUserHandler(userController, console));
        handlers.put(MenuOption.LOGIN,       new LoginHandler(userController, console, printer));

        // Authors – CRUDL (Unidad 3)
        handlers.put(MenuOption.CREATE_AUTHOR, new CreateAuthorHandler(authorController, console));
        handlers.put(MenuOption.UPDATE_AUTHOR, new UpdateAuthorHandler(authorController, console));
        handlers.put(MenuOption.FIND_AUTHOR,   new FindAuthorByIdHandler(authorController, console));
        handlers.put(MenuOption.LIST_AUTHORS,  new ListAuthorsHandler(authorController, console));
        handlers.put(MenuOption.DELETE_AUTHOR, new DeleteAuthorHandler(authorController, console));

        // Authors – 5 Consultas (Unidad 4 – CEA 21)
        handlers.put(MenuOption.FIND_AUTHOR_BY_EMAIL,
                new GetAuthorByEmailHandler(authorController, console));
        handlers.put(MenuOption.FIND_AUTHORS_BY_NAME,
                new GetAuthorsByNameHandler(authorController, console));
        handlers.put(MenuOption.FIND_AUTHORS_BY_WORK_CENTER,
                new GetAuthorsByWorkCenterHandler(authorController, console));
        handlers.put(MenuOption.FIND_AUTHORS_BY_COUNTRY,
                new GetAuthorsByCountryHandler(authorController, console));

        return handlers;
    }

    // ── Menú ──────────────────────────────────────────────────────────────────

    private void printMenu() {
        console.println();
        console.println(MENU_BORDER);
        console.println("    Main Menu");
        console.println(MENU_BORDER);
        for (final MenuOption option : MenuOption.values()) {
            console.printf("    [%d] %s%n", option.getNumber(), option.getDescription());
        }
        console.println(MENU_BORDER);
    }
}