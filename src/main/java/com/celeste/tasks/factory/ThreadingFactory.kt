package com.celeste.tasks.factory

import lombok.AccessLevel
import lombok.NoArgsConstructor
import java.util.concurrent.BlockingQueue
import java.util.concurrent.ExecutorService
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledThreadPoolExecutor
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

@NoArgsConstructor(access = AccessLevel.PRIVATE)
object ThreadingFactory {

  fun threadPool(
    minThreads: Int = 0,
    maxThreads: Int = Int.MAX_VALUE,
    keepAlive: Long = 5L,
    time: TimeUnit = TimeUnit.MINUTES,
    queue: BlockingQueue<Runnable> = SynchronousQueue(),
    factory: ThreadFactory? = null,
  ): ExecutorService {
    if (factory == null) {
      return ThreadPoolExecutor(minThreads, maxThreads, keepAlive, time, queue)
    }

    return ThreadPoolExecutor(minThreads, maxThreads, keepAlive, time, queue, factory)
  }

  fun fixedThreadPool(
    threads: Int = 1,
    keepAlive: Long = 0L,
    time: TimeUnit = TimeUnit.MILLISECONDS,
    queue: BlockingQueue<Runnable> = SynchronousQueue(),
    factory: ThreadFactory? = null,
  ): ExecutorService {
    if (factory == null) {
      return ThreadPoolExecutor(threads, threads, keepAlive, time, queue)
    }

    return ThreadPoolExecutor(threads, threads, keepAlive, time, queue, factory)
  }

  fun scheduledThreadPool(
    minThreads: Int = 0,
    factory: ThreadFactory? = null
  ): ScheduledExecutorService {
    if (factory == null) {
      return ScheduledThreadPoolExecutor(minThreads)
    }

    return ScheduledThreadPoolExecutor(minThreads, factory)
  }

}