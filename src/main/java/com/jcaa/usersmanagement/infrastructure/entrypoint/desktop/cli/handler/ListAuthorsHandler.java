package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ListAuthorsHandler implements OperationHandler {
    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {
        consoleIO.println("\n--- LIST OF AUTHORS ---");
        try {
            var authors = controller.getAllAuthors();
            if (authors.isEmpty()) {
                consoleIO.println("No authors found.");
            } else {
                authors.forEach(author -> consoleIO.println("- " + author));
            }
        } catch (Exception e) {
            consoleIO.println("ERROR: " + e.getMessage());
        }
    }
}