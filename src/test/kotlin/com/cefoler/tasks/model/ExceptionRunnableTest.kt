package com.cefoler.tasks.model

class ExceptionRunnableTest : Runnable {

    override fun run() {
        throw UnsupportedOperationException()
    }

}