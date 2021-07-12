package com.celeste.tasks.model.entity.type;

import com.google.common.collect.ImmutableList;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public enum StateType {

  NEW,
  RUNNABLE,
  BLOCKED,
  WAITING,
  TIME_WAITING,
  TERMINATED;

  private final List<String> names;

  StateType(final String... names) {
    this.names = ImmutableList.copyOf(names);
  }

  public static StateType getState(final String state) {
    return Arrays.stream(values())
        .filter(type -> type.getNames().contains(state.toUpperCase()))
        .findFirst()
        .orElseThrow(() -> new InvalidParameterException("Invalid state: " + state));
  }

  public static StateType getState(final String state, @Nullable final StateType orElse) {
    return Arrays.stream(values())
        .filter(type -> type.getNames().contains(state.toUpperCase()))
        .findFirst()
        .orElse(orElse);
  }

}
