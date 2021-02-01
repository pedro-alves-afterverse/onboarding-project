package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.model.SKU
import com.playkids.onboarding.core.persistence.SKUDAO

class SKUService(
    private val skuDAO: SKUDAO,
) {
    suspend fun create(item: SKU){
        skuDAO.create(item)
    }

    suspend fun find(id: String): SKU? {
        return skuDAO.find(id)
    }
}