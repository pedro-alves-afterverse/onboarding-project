package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.dto.CreateSkuDTO
import com.playkids.onboarding.core.model.SKU
import com.playkids.onboarding.core.persistence.SKUDAO

class SKUService(
    private val skuDAO: SKUDAO,
) {
    suspend fun create(skuDTO: CreateSkuDTO): SKU {
        val sku = SKU.skuFactory(skuDTO)
        skuDAO.create(sku)
        return sku
    }

    suspend fun find(id: String): SKU? {
        return skuDAO.find(id)
    }
}