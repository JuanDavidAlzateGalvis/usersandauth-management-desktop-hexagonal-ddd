package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateAuthorCommand;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateAuthorHandler implements OperationHandler {
    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {
        consoleIO.println("\n--- UPDATE AUTHOR ---");
        final String id = consoleIO.readRequired("Enter author ID to update: ");
        final String fullName = consoleIO.readRequired("Enter new full name: ");
        final String workCenter = consoleIO.readRequired("Enter new work center: ");
        final String email = consoleIO.readRequired("Enter new email: ");

        try {
            var response = controller.updateAuthor(new UpdateAuthorCommand(id, fullName, workCenter, email));
            consoleIO.println("SUCCESS: Author updated -> " + response);
        } catch (Exception e) {
            consoleIO.println("ERROR: " + e.getMessage());
        }
    }
}