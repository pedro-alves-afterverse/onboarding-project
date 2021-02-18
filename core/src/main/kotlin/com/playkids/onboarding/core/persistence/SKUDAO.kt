package com.playkids.onboarding.core.persistence

import com.playkids.onboarding.core.model.SKU
import com.playkids.onboarding.core.model.SKUId

interface SKUDAO {
    suspend fun create(sku: SKU)
    suspend fun find(skuId: SKUId): SKU?
    suspend fun findAll(): List<SKU>?
}