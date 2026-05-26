package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAuthorHandler implements OperationHandler {
    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {
        consoleIO.println("\n--- DELETE AUTHOR ---");
        final String id = consoleIO.readRequired("Enter author ID to delete: ");
        try {
            controller.deleteAuthor(id);
            consoleIO.println("SUCCESS: Author deleted.");
        } catch (Exception e) {
            consoleIO.println("ERROR: " + e.getMessage());
        }
    }
}