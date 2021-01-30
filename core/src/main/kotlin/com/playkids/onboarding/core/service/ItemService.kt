package com.playkids.onboarding.core.service

import com.playkids.onboarding.api.extensions.logger
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

    //TODO: batch get not working
    suspend fun findAllByCategory(category: String): List<Item>? {
        val logger = logger<ItemService>()
        val items = itemDAO.findAllByCategory(category)
        logger.debug("Items")
        logger.debug(items.toString())
        return items
    }
}