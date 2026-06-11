package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthorByEmailHandler implements OperationHandler {

    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {

        consoleIO.println("\n--- SEARCH AUTHOR BY EMAIL ---");

        final String email =
                consoleIO.readRequired("Enter author email: ");

        try {

            final var response =
                    controller.getAuthorByEmail(email);

            consoleIO.println("AUTHOR FOUND:");
            consoleIO.println(response.toString());

        } catch (Exception e) {

            consoleIO.println("ERROR: " + e.getMessage());

        }
    }
}