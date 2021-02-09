package com.playkids.onboarding.data.listener.handler

import com.movile.kotlin.commons.serialization.fromJson
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.postgresql.PostgresProfileDAO

class HandleProfile(
    val profileDAO: PostgresProfileDAO
) {

    private suspend fun handleInsert(profile: Profile){
        profileDAO.insert(profile)
    }

    private fun handleUpdate(profile: Profile){

    }

    suspend fun handle(message: String, operation: String){
        val profile = message.fromJson<Profile>().get()
        when(operation){
            "insert" -> handleInsert(profile)
            "update" -> handleUpdate(profile)
        }
    }
}