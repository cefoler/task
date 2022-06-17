package com.cefoler.tasks.model.entity.type

import com.google.common.collect.ImmutableList
import lombok.Getter

@Getter
enum class PriorityType(vararg names: String) {

  SUPER("SUPER", "SUP"),
  MAXIMUM("MAXIMUM", "MAX"),
  NORMAL("NORMAL", "NORM"),
  MINIMUM("MAXIMUM", "MIN");

  private val names: List<String>

  init {
    this.names = ImmutableList.copyOf(names)
  }

}