package com.playkids.onboarding.data.listener.handler

import com.movile.kotlin.commons.serialization.fromJson
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.postgresql.PostgresProfileDAO
import com.playkids.onboarding.sqs.SQSHandler

class SQSEventHandler(
    val profileHandler: HandleProfile
): SQSHandler {
    override fun handle(message: String, attributes: Map<String, String>) {

        when(attributes.getValue("entity")){
            "Profile" -> profileHandler.handle(message, attributes.getValue("operation"))
        }
        val profile = message.fromJson<Profile>().get()

        println(profile.username)
        println(profile.region)

        println("SQS MESSAGE:")
        println(message)

        println("SQS ATTRIBUTES:")
        attributes.forEach {
            println(it.key)
            println(it.value)
        }

    }

//    private fun handleProfile(profile: Profile, operation: String){
//        when(operation){
//            "insert"
//        }
//    }


}