package com.celeste.tasks.factory;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TaskFactory {

  private static final TaskFactory INSTANCE;

  static {
    INSTANCE = new TaskFactory();
  }

  public static TaskFactory getInstance() {
    return INSTANCE;
  }

}
