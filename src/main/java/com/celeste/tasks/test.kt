package com.celeste.tasks

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class test {

  suspend fun test() = withContext(Dispatchers.IO) {

  }

}