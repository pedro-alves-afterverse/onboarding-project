package com.playkids.onboarding.data.listener.handler

import com.movile.kotlin.commons.serialization.fromJson
import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.util.Currencies
import com.playkids.onboarding.core.util.ProfileCurrencies
import com.playkids.onboarding.postgresql.PostgresProfileDAO

fun UpdateCurrencyDTO.getValues(): MutableMap<Currencies, Number> {
    val values = mutableMapOf<Currencies, Number>()
    coin?.run { values.put(ProfileCurrencies.COIN, this) }
    gem?.run { values.put(ProfileCurrencies.GEM, this) }
    money?.run { values.put(ProfileCurrencies.MONEY, this) }
    return values
}

class HandleProfile(
    private val profileDAO: PostgresProfileDAO
) {

    private suspend fun handleInsert(profile: Profile){
        profileDAO.create(profile)
    }

    private suspend fun handleUpdate(updateCurrency: UpdateCurrencyDTO){
        updateCurrency.getValues().forEach {
            profileDAO.updateCurrency(updateCurrency.profileId, it.key, it.value)
        }
    }

    suspend fun handle(message: String, operation: String){
        when(operation){
            "insert" -> handleInsert(message.fromJson<Profile>().get())
            "update" -> handleUpdate(message.fromJson<UpdateCurrencyDTO>().get())
        }
    }
}