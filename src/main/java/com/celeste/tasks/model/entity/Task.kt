package com.celeste.tasks.model.entity

import com.celeste.tasks.model.entity.type.ExecutorType
import com.celeste.tasks.model.entity.type.PriorityType
import com.celeste.tasks.model.entity.type.StateType
import java.io.Serializable

interface Task : Serializable {

  fun next()

  fun close()

  fun clone(): TaskImpl

  fun compareTo(task: TaskImpl): Int

  fun getRun(): Long

  fun getPeriod(): Long

  fun getPriority(): PriorityType

  fun getState(): StateType

  fun setState(state: StateType)

  fun getExecutor(): ExecutorType

  fun runnable(): Runnable

}