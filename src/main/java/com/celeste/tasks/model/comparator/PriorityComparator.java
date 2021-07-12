package com.celeste.tasks.model.comparator;

import com.celeste.tasks.model.entity.Task;
import java.util.Comparator;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PriorityComparator implements Comparator<Task> {

  private static final PriorityComparator INSTANCE;

  static {
    INSTANCE = new PriorityComparator();
  }

  @Override
  public int compare(final Task task1, final Task task2) {
    final int priority1 = task1.getPriority().ordinal();
    final int priority2 = task2.getPriority().ordinal();

    return Integer.compare(priority1, priority2);
  }

  public static PriorityComparator getInstance() {
    return INSTANCE;
  }

}
