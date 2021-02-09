package com.playkids.onboarding.data.listener.configuration

import com.playkids.onboarding.data.listener.configuration.Configuration.dataListener
import com.playkids.onboarding.data.listener.handler.SQSEventHandler
import kotlinx.coroutines.runBlocking

object LifecycleConfiguration {

    suspend fun start() {
        registerShutdownHook()
        dataListener.start().join()
    }

    private fun registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                runBlocking {
                    dataListener.stop()
                    println("Shutdown!!!")
                }
            }
        )
    }

}