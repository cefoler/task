package com.cefoler.tasks.model

import java.util.regex.Pattern

class RegexRunnableTest : Runnable {

    // Using Pattern compile as it is a slow process.
    override fun run() {
        val before = System.currentTimeMillis()
        Pattern.compile("\\/\\/(?:([^\\:\\/\\?\\#\\[\\]\\@]+)(?::([^\\:\\/\\?\\#\\[\\]\\@]+))?@)?([\\w\\.\\-]+(?::\\d+)?(?:,[\\w\\.\\-]+(?::\\d+)?)*)(?:\\/([\\w\\.\\-]+))")
        val after = System.currentTimeMillis()

        val total = after - before
        println("Duration time of runnable: $total")
    }

}