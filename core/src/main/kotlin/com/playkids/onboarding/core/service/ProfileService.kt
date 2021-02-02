package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.excption.EntityNotFoundException
import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.model.SKUId
import com.playkids.onboarding.core.persistence.ProfileDAO
import com.playkids.onboarding.core.persistence.SKUDAO
import com.playkids.onboarding.core.util.ChooseValue

class ProfileService(
    private val profileDAO: ProfileDAO,
    private val skuDAO: SKUDAO
) {
    suspend fun create(profile: Profile){
        profileDAO.create(profile)
    }

    suspend fun find(id: ProfileId): Profile?{
        return profileDAO.find(id)
    }

    suspend fun addItem(profileId: ProfileId, itemIds: List<ItemId>){
        return profileDAO.addItem(profileId, itemIds)
    }

    suspend fun addSku(profileId: ProfileId, skuId: SKUId){
        val sku = skuDAO.find(skuId) ?: throw EntityNotFoundException("SKU with id $skuId doesn't exists")
        //{CHAMADA DE API PARA VERIFICAR A COMPRA}
        profileDAO.updateCurrency(profileId, "+", "coin", ChooseValue(sku.coin, null))
        profileDAO.updateCurrency(profileId, "+", "gem", ChooseValue(sku.gem, null))
        profileDAO.updateCurrency(profileId, operation = "+", currency = "moneySpent", ChooseValue(null, sku.price))
    }
}