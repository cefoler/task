package com.celeste.tasks.model.entity;

import com.celeste.tasks.model.entity.type.PriorityType;
import com.celeste.tasks.model.entity.type.ExecutorType;
import com.celeste.tasks.model.entity.type.StateType;
import java.io.Serializable;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

@Data
@Builder
@AllArgsConstructor
public final class Task implements Serializable, Cloneable, Comparable<Task> {

  private final UUID id;

  private long run;
  private long period;

  private TimeUnit time;
  private Process process;

  private ExecutorType executor;
  private PriorityType priority;

  private StateType state;

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
  public String toString() {
    return "Task - #" + id + " {"
        + "id=" + id
        + ", run=" + run
        + ", period=" + period
        + ", time=" + time
        + ", process=" + process
        + ", type=" + executor
        + ", priority=" + priority + "}";
  }

}
