package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.command.CreateAuthorCommand;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateAuthorHandler implements OperationHandler {
    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {
        consoleIO.println("\n--- CREATE AUTHOR ---");
        final String id = UUID.randomUUID().toString();
        final String fullName = consoleIO.readRequired("Enter full name: ");
        final String workCenter = consoleIO.readRequired("Enter work center: ");
        final String email = consoleIO.readRequired("Enter email: ");

        try {
            var response = controller.createAuthor(new CreateAuthorCommand(id, fullName, workCenter, email));
            consoleIO.println("SUCCESS: Author created -> " + response);
        } catch (Exception e) {
            consoleIO.println("ERROR: " + e.getMessage());
        }
    }
}