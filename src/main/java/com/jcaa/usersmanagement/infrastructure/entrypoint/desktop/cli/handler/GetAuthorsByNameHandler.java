package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthorsByNameHandler implements OperationHandler {

    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {

        consoleIO.println("\n--- FIND AUTHORS BY NAME ---");

        final String name =
                consoleIO.readRequired("Enter name: ");

        try {

            controller.getAuthorsByName(name)
                    .forEach(author ->
                            consoleIO.println(author.toString()));

        } catch (Exception e) {

            consoleIO.println("ERROR: " + e.getMessage());

        }
    }
}