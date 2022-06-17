package com.cefoler.tasks.model.comparator

import com.cefoler.tasks.model.entity.Task
import lombok.AccessLevel
import lombok.NoArgsConstructor

@NoArgsConstructor(access = AccessLevel.PRIVATE)
class PriorityComparator : Comparator<Task> {

  override fun compare(task1: Task, task2: Task): Int {
    val priority1 = task1.getPriority().ordinal
    val priority2 = task2.getPriority().ordinal

    return Integer.reverse(Integer.compare(priority1, priority2))
  }

  companion object {
    val INSTANCE: PriorityComparator = PriorityComparator()
  }

}