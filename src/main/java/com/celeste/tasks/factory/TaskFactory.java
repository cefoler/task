package com.celeste.tasks.factory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
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

  public void start() {

  }

  public void start(final ExecutorService executor) {

  }

  public void start(final ScheduledExecutorService scheduled) {

  }

  public void start(final ExecutorService executor, final ScheduledExecutorService scheduled) {

  }

  public static TaskFactory getInstance() {
    return INSTANCE;
  }

}
