package com.playkids.onboarding.core.persistence

import com.playkids.onboarding.core.model.Item
import com.playkids.onboarding.core.model.ItemId

interface ItemDAO {
    suspend fun create(item: Item)
    suspend fun find(category: String, itemId: ItemId): Item?
    suspend fun findAllByCategory(category: String): List<Item>?
}