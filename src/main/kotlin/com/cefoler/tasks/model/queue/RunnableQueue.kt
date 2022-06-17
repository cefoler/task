package com.cefoler.tasks.model.queue

import com.google.common.collect.ForwardingQueue
import lombok.RequiredArgsConstructor
import java.util.*
import java.util.function.Supplier

@RequiredArgsConstructor
class RunnableQueue<T>(
  private val queue: Queue<T>,
  private val runnable: Runnable,
) : ForwardingQueue<T>() {

  constructor(supplier: Supplier<Queue<T>>, runnable: Runnable) : this(supplier.get(), runnable)

  override fun add(result: T): Boolean {
    val status = super.add(result)
    val thread = Thread(runnable)

    thread.isDaemon = true
    thread.start()

    return status
  }

  override fun addAll(collection: Collection<T>): Boolean {
    val status = super.addAll(collection)
    val thread = Thread(runnable)

    thread.isDaemon = true
    thread.start()

    return status
  }

  override fun delegate(): Queue<T> {
    return queue
  }

}