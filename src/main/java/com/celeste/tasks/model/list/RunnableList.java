package com.celeste.tasks.model.list;

import com.google.common.collect.ForwardingList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class RunnableList<T> extends ForwardingList<T> {

  private final List<T> list;
  private final Runnable runnable;

  public RunnableList(final Supplier<List<T>> supplier, final Runnable runnable) {
    this(supplier.get(), runnable);
  }

  public boolean addDefault(final T result) {
    return super.add(result);
  }

  public boolean addAllDefault(final Collection<? extends T> collection) {
    return super.addAll(collection);
  }

  @Override
  public boolean add(@NotNull final T result) {
    final boolean status = super.add(result);
    final Thread thread = new Thread(runnable);
    thread.setDaemon(true);
    thread.start();

    return status;
  }

  @Override
  public boolean addAll(@NotNull final Collection<? extends T> collection) {
    final boolean status = super.addAll(collection);
    final Thread thread = new Thread(runnable);
    thread.setDaemon(true);
    thread.start();

    return status;
  }

  @Override
  protected List<T> delegate() {
    return list;
  }

}
