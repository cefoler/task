package com.celeste.tasks.factory;

import com.celeste.tasks.controller.TaskController;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

  public TaskController start() {
    final ExecutorService executor = ThreadingFactory.threadPool();

    return new TaskController(executor);
  }

  public TaskController start(final ExecutorService executor) {
    return new TaskController(executor);
  }

  public static TaskFactory getInstance() {
    return INSTANCE;
  }

}
