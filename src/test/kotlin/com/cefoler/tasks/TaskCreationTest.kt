package com.cefoler.tasks

import com.cefoler.tasks.controller.TaskController
import com.cefoler.tasks.factory.TaskFactory
import com.cefoler.tasks.model.ExceptionRunnableTest
import com.cefoler.tasks.model.RegexRunnableTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows

class TaskCreationTest {

    private val regexRunnable: Runnable
    private val exceptionRunnable: Runnable

    private lateinit var controller: TaskController

    init {
        regexRunnable = RegexRunnableTest()
        exceptionRunnable = ExceptionRunnableTest()
    }

    @BeforeEach
    fun prepare() {
        controller = TaskFactory.INSTANCE.create()
    }

    @Test
    fun `when execute runnable, should run without any exceptions`() {
        assertDoesNotThrow { controller.execute(regexRunnable) }
    }

    @Test
    fun `when execute runnable, should run and throw exception`() {
        assertThrows<UnsupportedOperationException> { controller.execute(exceptionRunnable) }
    }

}