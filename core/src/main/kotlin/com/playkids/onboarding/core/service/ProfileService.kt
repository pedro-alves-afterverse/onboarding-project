package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.dto.CreateProfileDTO
import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.excption.EntityNotFoundException
import com.playkids.onboarding.core.excption.NotEnoughCurrencyException
import com.playkids.onboarding.core.excption.UserHasItemException
import com.playkids.onboarding.core.model.*
import com.playkids.onboarding.core.persistence.ItemDAO
import com.playkids.onboarding.core.persistence.ProfileDAO
import com.playkids.onboarding.core.persistence.SKUDAO
import com.playkids.onboarding.core.util.Currencies
import com.playkids.onboarding.core.util.ItemCurrencies
import com.playkids.onboarding.core.util.ProfileCurrencies

class ProfileService(
    private val profileDAO: ProfileDAO,
    private val skuDAO: SKUDAO,
    private val itemDAO: ItemDAO
) {
    suspend fun create(profileDTO: CreateProfileDTO): Profile {
        val profile = Profile(profileDTO)
        profileDAO.create(profile)
        return profile
    }

    suspend fun find(id: ProfileId): Profile?{
        return profileDAO.find(id)
    }

    suspend fun addItem(profileId: ProfileId, itemId: ItemId, itemCategory: String){
        return profileDAO.addItem(profileId, listOf("$itemCategory:$itemId"))
    }

    suspend fun buyItem(profileId: ProfileId, itemId: ItemId, itemCategory: String): UpdateCurrencyDTO{
        val item = itemDAO.find(itemCategory, itemId) ?: throw EntityNotFoundException("Item with id $itemId and category $itemCategory doesn't exists")
        val itemKey = ItemKey.fromItem(item)
        val (profileItems, currencyAmount) = profileDAO.getItemsAndCurrency(profileId, item.currency) ?: throw EntityNotFoundException("profile with id $profileId doesn't exists")
        if (item.price > currencyAmount) throw NotEnoughCurrencyException("profile with id $profileId doesn't have enough ${item.currency} to buy item")
        if (itemKey.getKey() in profileItems.map { it.getKey() }) throw UserHasItemException("profile with id $profileId already has item of id $itemId")
        profileDAO.updateCurrency(profileId, item.currency, (-item.price))
        profileDAO.addItem(profileId, listOf("$itemCategory:$itemId"))

        return when(item.currency){
            ItemCurrencies.COIN -> UpdateCurrencyDTO(profileId = profileId, coin = (-item.price))
            ItemCurrencies.GEM -> UpdateCurrencyDTO(profileId = profileId, gem = (-item.price))
        }
    }

    suspend fun addSku(profileId: ProfileId, skuId: SKUId): UpdateCurrencyDTO{
        val sku = skuDAO.find(skuId) ?: throw EntityNotFoundException("SKU with id $skuId doesn't exists")
        //{API CALL TO VERIFY PURCHASE}
        profileDAO.updateCurrency(profileId, ProfileCurrencies.COIN, sku.coin)
        profileDAO.updateCurrency(profileId, ProfileCurrencies.GEM, sku.gem)
        profileDAO.updateCurrency(profileId, ProfileCurrencies.MONEY, sku.price)

        return UpdateCurrencyDTO(profileId, sku.coin, sku.gem, sku.price)
    }

    suspend fun getProfileItemsByCategory(profileId: ProfileId, category: String): List<Item?>? {
        val itemsKeys = profileDAO.getProfileItems(profileId)
        return itemsKeys?.filter { it.category == category }?.map { itemDAO.find(it.category, it.id) }
    }

    suspend fun getProfileCurrency(profileId: ProfileId): Map<Currencies, Int>? =
        profileDAO.getProfileCurrency(profileId)
}