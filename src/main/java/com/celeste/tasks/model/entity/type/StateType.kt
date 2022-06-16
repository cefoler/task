package com.celeste.tasks.model.entity.type

import com.google.common.collect.ImmutableList
import lombok.Getter

@Getter
enum class StateType(vararg names: String) {

  NEW("NEW"),
  RUNNABLE("RUNNABLE"),
  WAITING("WAITING"),
  TERMINATED("TERMINATED");

  private val names: List<String>

  init {
    this.names = ImmutableList.copyOf(names)
  }

}