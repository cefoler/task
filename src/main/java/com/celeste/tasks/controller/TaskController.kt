package com.celeste.tasks.controller

import com.celeste.tasks.model.comparator.PriorityComparator
import com.celeste.tasks.model.entity.Task
import com.celeste.tasks.model.entity.TaskImpl
import com.celeste.tasks.model.entity.type.ExecutorType
import com.celeste.tasks.model.entity.type.PriorityType
import com.celeste.tasks.model.entity.type.StateType
import com.celeste.tasks.model.list.RunnableList
import com.celeste.tasks.model.queue.RunnableQueue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lombok.SneakyThrows
import java.util.PriorityQueue
import java.util.Queue
import java.util.concurrent.Callable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Future
import java.util.concurrent.TimeUnit
import java.util.function.Supplier

class TaskController(
  private val executor: ExecutorService,
  private val tasks: RunnableQueue<Task> = RunnableQueue(QUEUE_SUPPLIER) { this.run() },
  private val waiting: RunnableList<Task> = RunnableList(LIST_SUPPLIER) { this.run() },
  private var active: Boolean = true,
  private var running: Boolean = false,
) {

  @Synchronized
  fun shutdown() {
    active = false
    running = false

    tasks.clear()
    waiting.clear()
    executor.shutdown()
  }

  fun execute(runnable: Runnable) {
    runnable.run()
  }

  fun executeAsync(runnable: Runnable) {
    executor.execute(runnable)
  }

  @SneakyThrows
  fun <T> submit(callable: Callable<T>): T {
    return callable.call()
  }

  suspend fun submitAsync(runnable: Runnable): Future<*> = withContext(DISPATCHER) {
    return@withContext executor.submit(runnable)
  }

  suspend fun <T> submitAsync(callable: Callable<T>): Future<T> = withContext(DISPATCHER) {
    return@withContext executor.submit(callable)
  }

  suspend fun <T> submitAsync(runnable: Runnable, result: T): Future<T> = withContext(DISPATCHER) {
    return@withContext executor.submit(runnable, result)
  }

  suspend fun runAsync(runnable: Runnable) = withContext(DISPATCHER) {
    return@withContext CompletableFuture.runAsync(runnable, executor)
  }

  suspend fun <T> supplyAsync(supplier: Supplier<T>) = withContext(DISPATCHER) {
    return@withContext CompletableFuture.supplyAsync(supplier, executor)
  }

  fun wait(
    runnable: Runnable,
    delay: Long,
    time: TimeUnit = TimeUnit.SECONDS,
    priority: PriorityType = PriorityType.NORMAL
  ): Task {
    val task: Task = TaskImpl(
      runnable = runnable,
      run = delay.calculateRun(time),
      period = -1,
      time = time,
      priority = priority
    )

    waiting.add(task)
    return task
  }

  suspend fun waitAsync(
    runnable: Runnable,
    delay: Long,
    time: TimeUnit = TimeUnit.SECONDS,
    priority: PriorityType = PriorityType.NORMAL
  ): Task = withContext(DISPATCHER) {
    val task: Task = TaskImpl(
      runnable = runnable,
      run = delay.calculateRun(time),
      period = -1,
      time = time,
      priority = priority,
      executor = ExecutorType.ASYNC,
    )

    waiting.add(task)
    return@withContext task
  }

  fun timer(
    runnable: Runnable,
    delay: Long,
    period: Long,
    time: TimeUnit = TimeUnit.SECONDS,
    priority: PriorityType = PriorityType.NORMAL
  ): Task {
    val task: Task = TaskImpl(
      runnable = runnable,
      run = delay.calculateRun(time),
      period = period,
      time = time,
      priority = priority
    )

    waiting.add(task)
    return task
  }

  suspend fun timerAsync(
    runnable: Runnable,
    delay: Long,
    period: Long,
    time: TimeUnit = TimeUnit.SECONDS,
    priority: PriorityType = PriorityType.NORMAL
  ): Task = withContext(DISPATCHER) {
    val task: Task = TaskImpl(
      runnable = runnable,
      run = delay.calculateRun(time),
      period = period,
      time = time,
      priority = priority,
      executor = ExecutorType.ASYNC,
    )

    waiting.add(task)
    return@withContext task
  }

  @SneakyThrows
  private fun run() {
    if (running) {
      return
    }

    synchronized(this) {
      running = true
      while (active) {
        // wait(10)
        if (tasks.isEmpty()) {
          if (waiting.isEmpty()) {
            break
          }

          tasks.addAll(waiting)
          waiting.clear()
          continue
        }

        val task = tasks.poll()
          ?: return@synchronized

        if (task.getState() == StateType.TERMINATED) {
          continue
        }

        if (task.getRun() > System.currentTimeMillis()) {
          waiting.add(task)
          continue
        }

        val runnable = task.runnable()
        task.setState(StateType.RUNNABLE)
        
        when (task.getExecutor()) {
          SYNC -> runnable.run()
          ASYNC -> executor.execute(runnable)
        }

        if (task.getPeriod() < 0) {
          task.close()
          continue
        }

        task.next()
        waiting.add(task)
      }

      running = false
    }
  }


  private fun Long.calculateRun(time: TimeUnit): Long {
    return System.currentTimeMillis() + time.toMillis(this)
  }


  companion object {
    private val QUEUE_SUPPLIER: Supplier<Queue<Task>> =
      Supplier { PriorityQueue(PriorityComparator.INSTANCE) }
    private val LIST_SUPPLIER: Supplier<List<Task>> = Supplier { ArrayList(0) }

    private val DISPATCHER = Dispatchers.IO
  }

}