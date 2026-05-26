package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FindAuthorByIdHandler implements OperationHandler {
    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {
        consoleIO.println("\n--- FIND AUTHOR ---");
        final String id = consoleIO.readRequired("Enter author ID: ");
        try {
            var response = controller.getAuthorById(id);
            consoleIO.println("FOUND: " + response);
        } catch (Exception e) {
            consoleIO.println("ERROR: " + e.getMessage());
        }
    }
}