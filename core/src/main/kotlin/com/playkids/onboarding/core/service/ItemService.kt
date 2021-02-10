package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.excption.EntityNotFoundException
import com.playkids.onboarding.core.model.Item
import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.persistence.ItemDAO
import com.playkids.onboarding.core.persistence.ProfileDAO

class ItemService(
    private val itemDAO: ItemDAO,
    private val profileDAO: ProfileDAO
) {
    suspend fun create(item: Item){
        itemDAO.create(item)
    }

    suspend fun find(category: String, itemId: ItemId): Item? {
        return itemDAO.find(category, itemId)
    }

    suspend fun findAllByCategory(category: String): List<Item>? {
        return itemDAO.findAllByCategory(category)
    }

    suspend fun findAllByCategoryForUser(category: String, profileId: ProfileId): List<Item>? {
        val profileItems = profileDAO.getProfileItems(profileId) ?: throw EntityNotFoundException("No profile with id $profileId")
        val items = itemDAO.findAllByCategory(category)
        return items?.filter { "${it.category}:${it.id}" !in profileItems.map { p -> p.getKey() } }
    }
}