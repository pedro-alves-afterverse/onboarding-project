package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.dto.CreateProfileDTO
import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.events.emitter.ProfileEventEmitter
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
    private val itemDAO: ItemDAO,
    private val eventEmitter: ProfileEventEmitter
) {
    suspend fun create(profileDTO: CreateProfileDTO): Profile {
        val profile = Profile(profileDTO)
        profileDAO.create(profile)
        eventEmitter.handleInsert(profile)
        return profile
    }

    suspend fun find(id: ProfileId): Profile?{
        return profileDAO.find(id)
    }

    suspend fun addItem(profileId: ProfileId, itemId: ItemId, itemCategory: String){
        return profileDAO.addItem(profileId, listOf("$itemCategory:$itemId"))
    }

    suspend fun buyItem(profileId: ProfileId, itemId: ItemId, itemCategory: String){
        val item = itemDAO.find(itemCategory, itemId) ?: throw EntityNotFoundException("Item with id $itemId and category $itemCategory doesn't exists")
        val itemKey = ItemKey.fromItem(item)
        val (profileItems, currencyAmount) = profileDAO.getItemsAndCurrency(profileId, item.currency) ?: throw EntityNotFoundException("profile with id $profileId doesn't exists")
        require (item.price < currencyAmount){"profile with id $profileId doesn't have enough ${item.currency} to buy item"}
        if (itemKey in profileItems) throw UserHasItemException("profile with id $profileId already has item of id $itemId")
        profileDAO.updateCurrency(profileId, item.currency, (-item.price))
        profileDAO.addItem(profileId, listOf(itemKey.getKey()))

        val updateCurrencyDTO = when(item.currency){
            ItemCurrencies.COIN -> UpdateCurrencyDTO(profileId = profileId, coin = (-item.price))
            ItemCurrencies.GEM -> UpdateCurrencyDTO(profileId = profileId, gem = (-item.price))
        }

        eventEmitter.handleUpdate(updateCurrencyDTO)
    }

    suspend fun addSku(profileId: ProfileId, skuId: SKUId){
        val sku = skuDAO.find(skuId) ?: throw EntityNotFoundException("SKU with id $skuId doesn't exists")
        //{API CALL TO VERIFY PURCHASE}
        profileDAO.updateCurrency(profileId, ProfileCurrencies.COIN, sku.coin)
        profileDAO.updateCurrency(profileId, ProfileCurrencies.GEM, sku.gem)
        profileDAO.updateCurrency(profileId, ProfileCurrencies.MONEY, sku.price)

        val updateCurrencyDTO = UpdateCurrencyDTO(profileId, sku.coin, sku.gem, sku.price)

        eventEmitter.handleUpdate(updateCurrencyDTO)
    }

    suspend fun getProfileItemsByCategory(profileId: ProfileId, category: String): List<Item?> {
        val itemsKeys = profileDAO.getProfileItems(profileId)
        return itemsKeys.filter { it.category == category }.map { itemDAO.find(it.category, it.id) }
    }

    suspend fun getProfileCurrency(profileId: ProfileId): Map<Currencies, Int>? =
        profileDAO.getProfileCurrency(profileId)
}