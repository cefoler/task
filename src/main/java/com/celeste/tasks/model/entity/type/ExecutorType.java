package com.celeste.tasks.model.entity.type;

import com.google.common.collect.ImmutableList;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public enum ExecutorType {

  SYNC("SYNC"),
  ASYNC("ASYNC");

  private final List<String> names;

  ExecutorType(final String... names) {
    this.names = ImmutableList.copyOf(names);
  }

  public static ExecutorType getTask(final String task) {
    return Arrays.stream(values())
        .filter(type -> type.getNames().contains(task.toUpperCase()))
        .findFirst()
        .orElseThrow(() -> new InvalidParameterException("Invalid task: " + task));
  }

  public static ExecutorType getTask(final String task, @Nullable final ExecutorType orElse) {
    return Arrays.stream(values())
        .filter(type -> type.getNames().contains(task.toUpperCase()))
        .findFirst()
        .orElse(orElse);
  }

}
