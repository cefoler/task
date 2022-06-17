package com.cefoler.tasks.model.entity

import com.cefoler.tasks.model.entity.type.ExecutorType
import com.cefoler.tasks.model.entity.type.PriorityType
import com.cefoler.tasks.model.entity.type.StateType
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.Setter
import lombok.SneakyThrows
import java.io.Closeable
import java.io.Serializable
import java.util.*
import java.util.concurrent.TimeUnit

@Getter
@Setter
@AllArgsConstructor
class TaskImpl(
  private val id: UUID = UUID.randomUUID(),
  private var run: Long,
  private val period: Long,
  private val time: TimeUnit = TimeUnit.SECONDS,
  private val runnable: Runnable,
  private val executor: ExecutorType = ExecutorType.SYNC,
  private val priority: PriorityType = PriorityType.NORMAL,
  private var state: StateType = StateType.NEW,
) : Task, Serializable, Closeable, Cloneable, Comparable<TaskImpl> {

  override operator fun next() {
    if (period >= 0) {
      run = System.currentTimeMillis() + time.toMillis(period)
    }

    state = StateType.WAITING
  }

  override fun close() {
    run = -1
    state = StateType.TERMINATED
  }

  override fun getRun(): Long {
    return run
  }

  override fun getPeriod(): Long {
    return period
  }

  override fun runnable(): Runnable {
    return runnable
  }

  override fun getExecutor(): ExecutorType {
    return executor
  }

  override fun getPriority(): PriorityType {
    return priority
  }

  override fun getState(): StateType {
    return state
  }

  override fun setState(state: StateType) {
    this.state = state
  }

  @SneakyThrows
  override fun clone(): TaskImpl {
    return super.clone() as TaskImpl
  }

  override fun compareTo(task: TaskImpl): Int {
    return task.id.compareTo(id)
  }

  override fun equals(`object`: Any?): Boolean {
    if (`object` !is TaskImpl) {
      return false
    }

    return `object`.id == id
  }

  override fun hashCode(): Int {
    return Objects.hash(id, run, period, time, runnable, executor, priority, state)
  }

}