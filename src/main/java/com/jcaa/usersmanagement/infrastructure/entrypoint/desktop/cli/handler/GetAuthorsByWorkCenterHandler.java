package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.AuthorController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthorsByWorkCenterHandler implements OperationHandler {

    private final AuthorController controller;
    private final ConsoleIO consoleIO;

    @Override
    public void handle() {

        consoleIO.println("\n--- FIND AUTHORS BY WORK CENTER ---");

        final String workCenter =
                consoleIO.readRequired("Enter work center: ");

        try {

            controller.getAuthorsByWorkCenter(workCenter)
                    .forEach(author ->
                            consoleIO.println(author.toString()));

        } catch (Exception e) {

            consoleIO.println("ERROR: " + e.getMessage());

        }
    }
}