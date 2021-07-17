package com.celeste.tasks.model.entity;

import com.celeste.tasks.model.entity.type.ExecutorType;
import com.celeste.tasks.model.entity.type.PriorityType;
import com.celeste.tasks.model.entity.type.StateType;
import java.io.Closeable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@AllArgsConstructor
public final class Task implements Serializable, Closeable, Cloneable, Comparable<Task> {

  private final UUID id;

  private long run;
  private long period;

  private TimeUnit time;
  private Runnable runnable;

  private ExecutorType executor;
  private PriorityType priority;

  private StateType state;

  public Task(final long run, final long period, final Runnable runnable) {
    this(run, period, TimeUnit.SECONDS, runnable);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable) {
    this(run, period, time, runnable, ExecutorType.SYNC);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final ExecutorType executor) {
    this(run, period, time, runnable, executor, PriorityType.NORMAL);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final PriorityType priority) {
    this(run, period, time, runnable, priority, StateType.NEW);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final StateType state) {
    this(run, period, time, runnable, ExecutorType.SYNC, state);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final ExecutorType executor, final PriorityType priority) {
    this(run, period, time, runnable, executor, priority, StateType.NEW);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final ExecutorType executor, final StateType state) {
    this(run, period, time, runnable, executor, PriorityType.NORMAL, state);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final PriorityType priority, final StateType state) {
    this(run, period, time, runnable, ExecutorType.SYNC, priority, state);
  }

  public Task(final long run, final long period, final TimeUnit time, final Runnable runnable,
      final ExecutorType executor, final PriorityType priority, final StateType state) {
    this(UUID.randomUUID(), run, period, time, runnable, executor, priority, state);
  }

  public Task(final long run, final long period, final Runnable runnable,
      final ExecutorType executor) {
    this(run, period, runnable, executor, PriorityType.NORMAL);
  }

  public Task(final long run, final long period, final Runnable runnable,
      final PriorityType priority) {
    this(run, period, runnable, priority, StateType.NEW);
  }

  public Task(final long run, final long period, final Runnable runnable, final StateType state) {
    this(run, period, runnable, ExecutorType.SYNC, state);
  }

  public Task(final long run, final long period, final Runnable runnable,
      final ExecutorType executor, final PriorityType priority) {
    this(run, period, runnable, executor, priority, StateType.NEW);
  }

  public Task(final long run, final long period, final Runnable runnable,
      final ExecutorType executor, final StateType state) {
    this(run, period, runnable, executor, PriorityType.NORMAL, state);
  }

  public Task(final long run, final long period, final Runnable runnable,
      final PriorityType priority, final StateType state) {
    this(run, period, runnable, ExecutorType.SYNC, priority, state);
  }

  public Task(final long run, final long period, final Runnable runnable,
      final ExecutorType executor, final PriorityType priority, final StateType state) {
    this(run, period, TimeUnit.SECONDS, runnable, executor, priority, state);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable) {
    this(id, run, period, TimeUnit.SECONDS, runnable);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable) {
    this(id, run, period, time, runnable, ExecutorType.SYNC);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable, final ExecutorType executor) {
    this(id, run, period, time, runnable, executor, PriorityType.NORMAL);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable, final PriorityType priority) {
    this(id, run, period, time, runnable, priority, StateType.NEW);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable, final StateType state) {
    this(id, run, period, time, runnable, ExecutorType.SYNC, state);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable, final ExecutorType executor, final PriorityType priority) {
    this(id, run, period, time, runnable, executor, priority, StateType.NEW);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable, final ExecutorType executor, final StateType state) {
    this(id, run, period, time, runnable, executor, PriorityType.NORMAL, state);
  }

  public Task(final UUID id, final long run, final long period, final TimeUnit time,
      final Runnable runnable, final PriorityType priority, final StateType state) {
    this(id, run, period, time, runnable, ExecutorType.SYNC, priority, state);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final ExecutorType executor) {
    this(id, run, period, runnable, executor, PriorityType.NORMAL);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final PriorityType priority) {
    this(id, run, period, runnable, priority, StateType.NEW);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final StateType state) {
    this(id, run, period, runnable, ExecutorType.SYNC, state);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final ExecutorType executor, final PriorityType priority) {
    this(id, run, period, runnable, executor, priority, StateType.NEW);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final ExecutorType executor, final StateType state) {
    this(id, run, period, runnable, executor, PriorityType.NORMAL, state);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final PriorityType priority, final StateType state) {
    this(id, run, period, runnable, ExecutorType.SYNC, priority, state);
  }

  public Task(final UUID id, final long run, final long period, final Runnable runnable,
      final ExecutorType executor, final PriorityType priority, final StateType state) {
    this(id, run, period, TimeUnit.SECONDS, runnable, executor, priority, state);
  }

  public void next() {
    if (period >= 0) {
      this.run = System.currentTimeMillis() + time.toMillis(period);
    }

    this.state = StateType.WAITING;
  }

  @Override
  public void close() {
    this.run = -1;
    this.state = StateType.TERMINATED;
  }

  @Override
  @SneakyThrows
  protected Task clone() {
    return (Task) super.clone();
  }

  @Override
  public int compareTo(final Task task) {
    return task.getId().compareTo(id);
  }

  @Override
  public boolean equals(final Object object) {
    if (!(object instanceof Task)) {
      return false;
    }

    final Task task = (Task) object;
    return task.getId().equals(id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, run, period, time, runnable, executor, priority, state);
  }

  @Override
  public String toString() {
    return "Task - " + id + " {"
        + "id=" + id
        + ", run=" + run
        + ", period=" + period
        + ", time=" + time
        + ", runnable=" + runnable
        + ", type=" + executor
        + ", priority=" + priority + "}";
  }

}
