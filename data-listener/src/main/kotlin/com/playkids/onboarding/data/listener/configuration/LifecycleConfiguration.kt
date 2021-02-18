package com.playkids.onboarding.data.listener.configuration

import com.playkids.onboarding.data.listener.configuration.Configuration.eventListener
import kotlinx.coroutines.runBlocking

object LifecycleConfiguration {

    suspend fun start() {
        registerShutdownHook()
        eventListener.start().join()
    }

    private fun registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(
            Thread {
                runBlocking {
                    eventListener.stop()
                    println("Shutdown!!!")
                }
            }
        )
    }

}