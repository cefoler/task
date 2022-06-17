package com.cefoler.tasks.model.entity.type

import com.google.common.collect.ImmutableList
import lombok.Getter

@Getter
enum class ExecutorType(vararg names: String) {

  SYNC("SYNC"),
  ASYNC("ASYNC");

  private val names: List<String>

  init {
    this.names = ImmutableList.copyOf(names)
  }

}