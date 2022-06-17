package com.cefoler.tasks.factory

import com.cefoler.tasks.controller.TaskController
import lombok.AccessLevel
import lombok.Getter
import lombok.NoArgsConstructor
import java.util.concurrent.ExecutorService

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class TaskFactory {

  fun create(): TaskController {
    val executor: ExecutorService = ThreadingFactory.threadPool()
    return TaskController(executor)
  }

  fun create(executor: ExecutorService): TaskController {
    return TaskController(executor)
  }

  companion object {
    val INSTANCE: TaskFactory = TaskFactory()
  }

}