package com.cefoler.tasks.model.list

import com.google.common.collect.ForwardingList
import lombok.RequiredArgsConstructor
import java.util.function.Supplier

@RequiredArgsConstructor
class RunnableList<T>(
  private val list: List<T>,
  private val runnable: Runnable,
) : ForwardingList<T>() {

  constructor(supplier: Supplier<List<T>>, runnable: Runnable) : this(supplier.get(), runnable)

  fun addDefault(result: T): Boolean {
    return super.add(result)
  }

  fun addAllDefault(collection: Collection<T>): Boolean {
    return super.addAll(collection)
  }

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

  override fun delegate(): List<T> {
    return list
  }

}