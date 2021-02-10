package com.playkids.onboarding.core.model

import com.playkids.onboarding.core.dto.CreateProfileDTO
import java.util.*

typealias ProfileId = String

enum class Regions {SA, CA, NA, AS, AF, EU, OC}

data class Profile(
    val id: ProfileId,
    val username: String,
    val items: List<String>,
    val gem: Int,
    val coin: Int,
    val moneySpent: Float,
    val region: Regions
){
    companion object{
        fun baseProfileFactory(profileDTO: CreateProfileDTO): Profile{
            return Profile(
                id = UUID.randomUUID().toString(),
                username = profileDTO.username,
                items = emptyList(),
                gem = 100,
                coin = 500,
                moneySpent = 0F,
                region = profileDTO.region
            )
        }
    }
}
