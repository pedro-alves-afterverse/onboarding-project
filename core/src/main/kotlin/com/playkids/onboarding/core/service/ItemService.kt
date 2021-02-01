package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.model.Item
import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.persistence.ItemDAO

class ItemService(
    private val itemDAO: ItemDAO
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
}