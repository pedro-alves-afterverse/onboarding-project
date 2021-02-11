package com.playkids.onboarding.api.sqs.handler

import com.movile.kotlin.commons.serialization.toJson
import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.sqs.EventEmitter

class SQSProfileHandler(
    private val eventEmitter: EventEmitter
){

    suspend fun handleInsert(profile: Profile){
        val sqsAttributes = mapOf(
            "entity" to "Profile",
            "operation" to "insert"
        )

        eventEmitter.sendEvent(profile.toJson().get(), sqsAttributes).join()
    }

    suspend fun handleUpdate(updateCurrencyDTO: UpdateCurrencyDTO){
        val sqsAttributes = mapOf(
            "entity" to "Profile",
            "operation" to "update"
        )

        eventEmitter.sendEvent(updateCurrencyDTO.toJson().get(), sqsAttributes).join()
    }
}
