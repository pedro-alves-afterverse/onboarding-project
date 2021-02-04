package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.excption.EntityNotFoundException
import com.playkids.onboarding.core.excption.NotEnoughCurrency
import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.model.SKUId
import com.playkids.onboarding.core.persistence.ItemDAO
import com.playkids.onboarding.core.persistence.ProfileDAO
import com.playkids.onboarding.core.persistence.SKUDAO
import com.playkids.onboarding.core.util.ChooseValue

class ProfileService(
    private val profileDAO: ProfileDAO,
    private val skuDAO: SKUDAO,
    private val itemDAO: ItemDAO
) {
    suspend fun create(profile: Profile){
        profileDAO.create(profile)
    }

    suspend fun find(id: ProfileId): Profile?{
        return profileDAO.find(id)
    }

    suspend fun addItem(profileId: ProfileId, itemId: ItemId, itemCategory: String){
        return profileDAO.addItem(profileId, listOf("$itemCategory:$itemId"))
    }

    suspend fun buyItem(profileId: ProfileId, itemId: ItemId, itemCategory: String, currency: String){
        val item = itemDAO.find(itemCategory, itemId) ?: throw EntityNotFoundException("Item with id $itemId and category $itemCategory doesn't exists")
        val price = when(currency){
            COIN -> item.coinPrice
            GEM -> item.gemPrice
            else -> {
                throw IllegalArgumentException("currency $currency doesn't exists")
            }
        } ?: throw IllegalArgumentException("Item doesn't have attribute $currency")
        val currencyAmount = profileDAO.getCurrency(profileId, currency) ?: throw EntityNotFoundException("profile with id $profileId doesn't exists")
        if (price > currencyAmount) throw NotEnoughCurrency("profile with id $profileId doesn't have enough $currency to buy item")
        profileDAO.updateCurrency(profileId, "-", currency, ChooseValue(price, null))
        profileDAO.addItem(profileId, listOf("$itemCategory:$itemId"))
    }

    suspend fun addSku(profileId: ProfileId, skuId: SKUId){
        val sku = skuDAO.find(skuId) ?: throw EntityNotFoundException("SKU with id $skuId doesn't exists")
        //{CHAMADA DE API PARA VERIFICAR A COMPRA}
        profileDAO.updateCurrency(profileId, "+", "coin", ChooseValue(sku.coin, null))
        profileDAO.updateCurrency(profileId, "+", "gem", ChooseValue(sku.gem, null))
        profileDAO.updateCurrency(profileId, operation = "+", currency = "moneySpent", ChooseValue(null, sku.price))
    }

    companion object {
        private const val COIN = "coin"
        private const val GEM = "gem"
    }
}