package com.playkids.onboarding.data.listener

import com.playkids.onboarding.data.listener.configuration.Configuration.dataListener
import com.playkids.onboarding.data.listener.configuration.LifecycleConfiguration
import com.playkids.onboarding.data.listener.handler.SQSEventHandler

suspend fun main(){
    LifecycleConfiguration.start()
}