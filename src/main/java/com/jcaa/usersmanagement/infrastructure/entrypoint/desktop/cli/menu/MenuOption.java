package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.menu;

import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MenuOption {

CREATE_AUTHOR(7, "Create Author"),
UPDATE_AUTHOR(8, "Update Author"),
FIND_AUTHOR(9, "Find Author By ID"),
LIST_AUTHORS(10, "List All Authors"),
DELETE_AUTHOR(11, "Delete Author"),

FIND_AUTHOR_BY_EMAIL(12, "Find Author By Email"),
FIND_AUTHORS_BY_NAME(13, "Find Authors By Name"),
FIND_AUTHORS_BY_WORK_CENTER(14, "Find Authors By Work Center"),
FIND_AUTHORS_BY_COUNTRY(15, "Find Authors By Country"),

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

