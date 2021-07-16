package com.celeste.tasks.model.queue;

import com.google.common.collect.ForwardingQueue;
import java.util.Collection;
import java.util.Queue;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class RunnableQueue<T> extends ForwardingQueue<T> {

  private final Queue<T> queue;
  private final Runnable runnable;

  public RunnableQueue(final Supplier<Queue<T>> supplier, final Runnable runnable) {
    this(supplier.get(), runnable);
  }

  @Override
  public boolean add(@NotNull final T result) {
    final boolean status = super.add(result);
    runnable.run();

    return status;
  }

  @Override
  public boolean addAll(@NotNull final Collection<? extends T> collection) {
    final boolean status = super.addAll(collection);
    runnable.run();

    return status;
  }

  @Override
  protected Queue<T> delegate() {
    return queue;
  }

}
