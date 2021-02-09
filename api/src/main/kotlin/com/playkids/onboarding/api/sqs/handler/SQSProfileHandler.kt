package com.playkids.onboarding.api.sqs.handler

import com.movile.kotlin.commons.serialization.toJson
import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.sqs.SQSEmitter

class SQSProfileHandler(
    private val sqsEmitter: SQSEmitter
){

    suspend fun handleInsert(profile: Profile){
        val sqsAttributes = mapOf(
            "entity" to "Profile",
            "operation" to "insert"
        )

        sqsEmitter.sendEvent(profile.toJson().get(), sqsAttributes).join()
    }

    suspend fun handleUpdate(updateCurrencyDTO: UpdateCurrencyDTO){
        val sqsAttributes = mapOf(
            "entity" to "Profile",
            "operation" to "update"
        )

        sqsEmitter.sendEvent(updateCurrencyDTO.toJson().get(), sqsAttributes).join()
    }
}
