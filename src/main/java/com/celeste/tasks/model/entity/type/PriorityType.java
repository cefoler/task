package com.celeste.tasks.model.entity.type;

import com.google.common.collect.ImmutableList;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public enum PriorityType {

  SUPER("SUPER", "SUP"),
  MAXIMUM("MAXIMUM", "MAX"),
  NORMAL("NORMAL", "NORM"),
  MINIMUM("MAXIMUM", "MIN");

  private final List<String> names;

  PriorityType(final String... names) {
    this.names = ImmutableList.copyOf(names);
  }

  public static PriorityType getPriority(final String priority) {
    return Arrays.stream(values())
        .filter(type -> type.getNames().contains(priority.toUpperCase()))
        .findFirst()
        .orElseThrow(() -> new InvalidParameterException("Invalid priority: " + priority));
  }

  public static PriorityType getPriority(final String task, @Nullable final PriorityType orElse) {
    return Arrays.stream(values())
        .filter(type -> type.getNames().contains(task.toUpperCase()))
        .findFirst()
        .orElse(orElse);
  }

}
