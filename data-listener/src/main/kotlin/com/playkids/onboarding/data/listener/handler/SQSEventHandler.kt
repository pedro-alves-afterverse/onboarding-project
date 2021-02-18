package com.playkids.onboarding.data.listener.handler

import com.playkids.onboarding.core.events.EventHandler
import javax.naming.OperationNotSupportedException

class SQSEventHandler(
    private val profileHandler: HandleProfile
): EventHandler {
    override suspend fun handle(message: String, attributes: Map<String, String>) {
        when(attributes.getValue("entity")){
            "Profile" -> profileHandler.handle(message, attributes.getValue("operation"))
            else -> {
                throw OperationNotSupportedException("Only Profile entity is accepted")
            }
        }
    }
}