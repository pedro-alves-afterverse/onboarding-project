package com.playkids.onboarding.sqs.emitter

import com.movile.kotlin.commons.serialization.toJson
import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.events.EventEmitter
import com.playkids.onboarding.core.events.emitter.ProfileEventEmitter

class SQSProfileEventEmitter(
    private val eventEmitter: EventEmitter
): ProfileEventEmitter{

    override suspend fun handleInsert(profile: Profile){
        val sqsAttributes = mapOf(
            "entity" to "Profile",
            "operation" to "insert"
        )

        eventEmitter.sendEvent(profile.toJson().get(), sqsAttributes).join()
    }

    override suspend fun handleUpdate(updateCurrencyDTO: UpdateCurrencyDTO){
        val sqsAttributes = mapOf(
            "entity" to "Profile",
            "operation" to "update"
        )

        eventEmitter.sendEvent(updateCurrencyDTO.toJson().get(), sqsAttributes).join()
    }
}
