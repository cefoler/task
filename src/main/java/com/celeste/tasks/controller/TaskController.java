package com.celeste.tasks.controller;

import com.celeste.tasks.model.comparator.PriorityComparator;
import com.celeste.tasks.model.entity.Task;
import com.celeste.tasks.model.entity.type.ExecutorType;
import com.celeste.tasks.model.entity.type.PriorityType;
import com.celeste.tasks.model.entity.type.StateType;
import com.celeste.tasks.model.list.RunnableList;
import com.celeste.tasks.model.queue.RunnableQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;
import lombok.SneakyThrows;

public final class TaskController {

  private static final Supplier<Queue<Task>> QUEUE_SUPPLIER;
  private static final Supplier<List<Task>> LIST_SUPPLIER;

  static {
    QUEUE_SUPPLIER = () -> new PriorityQueue<>(PriorityComparator.getInstance());
    LIST_SUPPLIER = ArrayList::new;
  }

  private final ExecutorService executor;

  private final RunnableQueue<Task> tasks;
  private final RunnableList<Task> waiting;

  private boolean active;
  private boolean running;

  public TaskController(final ExecutorService executor) {
    this.executor = executor;

    this.tasks = new RunnableQueue<>(QUEUE_SUPPLIER, this::run);
    this.waiting = new RunnableList<>(LIST_SUPPLIER, this::run);

    this.active = true;
    this.running = false;
  }

  public synchronized void shutdown() {
    this.active = false;
    this.running = false;

    tasks.clear();
    waiting.clear();

    executor.shutdown();
  }

  public void execute(final Runnable runnable) {
    runnable.run();
  }

  public void executeAsync(final Runnable runnable) {
    executor.execute(runnable);
  }

  @SneakyThrows
  public <T> T submit(final Callable<T> callable) {
    return callable.call();
  }

  public Future<?> submitAsync(final Runnable runnable) {
    return executor.submit(runnable);
  }

  public <T> Future<T> submitAsync(final Runnable runnable, final T result) {
    return executor.submit(runnable, result);
  }

  public <T> Future<T> submitAsync(final Callable<T> callable) {
    return executor.submit(callable);
  }

  public CompletableFuture<Void> runAsync(final Runnable runnable) {
    return CompletableFuture.runAsync(runnable, executor);
  }

  public <T> CompletableFuture<T> supplyAsync(final Supplier<T> supplier) {
    return CompletableFuture.supplyAsync(supplier, executor);
  }

  public Task wait(final Runnable runnable, final long delay) {
    return wait(runnable, delay, TimeUnit.SECONDS);
  }

  public Task wait(final Runnable runnable, final long delay, final TimeUnit time) {
    return wait(runnable, delay, time, PriorityType.NORMAL);
  }

  public Task wait(final Runnable runnable, final long delay, final PriorityType priority) {
    return wait(runnable, delay, TimeUnit.SECONDS, priority);
  }

  public Task wait(final Runnable runnable, final long delay, final TimeUnit time,
      final PriorityType priority) {
    final long run = System.currentTimeMillis() + time.toMillis(delay);

    final Task task = new Task(run, -1, time, runnable, priority);
    waiting.add(task);

    return task;
  }

  public Task waitAsync(final Runnable runnable, final long delay) {
    return waitAsync(runnable, delay, TimeUnit.SECONDS);
  }

  public Task waitAsync(final Runnable runnable, final long delay, final TimeUnit time) {
    return waitAsync(runnable, delay, time, PriorityType.NORMAL);
  }

  public Task waitAsync(final Runnable runnable, final long delay, final PriorityType priority) {
    return waitAsync(runnable, delay, TimeUnit.SECONDS, priority);
  }

  public Task waitAsync(final Runnable runnable, final long delay, final TimeUnit time,
      final PriorityType priority) {
    final long run = System.currentTimeMillis() + time.toMillis(delay);

    final Task task = new Task(run, -1, time, runnable, ExecutorType.ASYNC, priority);
    waiting.add(task);

    return task;
  }

  public Task timer(final Runnable runnable, final long delay, final long period) {
    return timer(runnable, delay, period, TimeUnit.SECONDS);
  }

  public Task timer(final Runnable runnable, final long delay, final long period,
      final TimeUnit time) {
    return timer(runnable, delay, period, time, PriorityType.NORMAL);
  }

  public Task timer(final Runnable runnable, final long delay, final long period,
      final PriorityType priority) {
    return timer(runnable, delay, period, TimeUnit.SECONDS, priority);
  }

  public Task timer(final Runnable runnable, final long delay, final long period,
      final TimeUnit time, final PriorityType priority) {
    final long run = System.currentTimeMillis() + time.toMillis(delay);

    final Task task = new Task(run, period, time, runnable, priority);
    waiting.add(task);

    return task;
  }

  public Task timerAsync(final Runnable runnable, final long delay, final long period) {
    return timerAsync(runnable, delay, period, TimeUnit.SECONDS);
  }

  public Task timerAsync(final Runnable runnable, final long delay, final long period,
      final TimeUnit time) {
    return timerAsync(runnable, delay, period, time, PriorityType.NORMAL);
  }

  public Task timerAsync(final Runnable runnable, final long delay, final long period,
      final PriorityType priority) {
    return timerAsync(runnable, delay, period, TimeUnit.SECONDS, priority);
  }

  public Task timerAsync(final Runnable runnable, final long delay, final long period,
      final TimeUnit time, final PriorityType priority) {
    final long run = System.currentTimeMillis() + time.toMillis(delay);

    final Task task = new Task(run, period, time, runnable, ExecutorType.ASYNC, priority);
    waiting.add(task);

    return task;
  }

  @SneakyThrows
  private void run() {
    if (running) {
      return;
    }

    synchronized (this) {
      this.running = true;

      while (active) {
        wait(10);

        if (tasks.isEmpty()) {
          if (waiting.isEmpty()) {
            break;
          }

          tasks.addAll(waiting);
          waiting.clear();
          continue;
        }

        final Task task = tasks.poll();

        if (task.getState().equals(StateType.TERMINATED)) {
          continue;
        }

        if (task.getRun() > System.currentTimeMillis()) {
          waiting.add(task);
          continue;
        }

        final Runnable runnable = task.getRunnable();
        task.setState(StateType.RUNNABLE);

        switch (task.getExecutor()) {
          case SYNC:
            final Thread thread = new Thread(runnable);
            thread.start();
            break;
          case ASYNC:
            executor.execute(runnable);
            break;
          default:
            throw new NullPointerException("Executor cannot be null");
        }

        if (task.getPeriod() < 0) {
          task.close();
          continue;
        }

        task.next();
        waiting.add(task);
      }

      this.running = false;
    }
  }

}
