package com.playkids.onboarding.core.model

import com.playkids.onboarding.core.dto.CreateSkuDTO
import java.util.*

typealias SKUId = String

data class SKU(
    val id: SKUId,
    val gem: Int,
    val coin: Int,
    val price: Float
) {
    companion object{
        fun skuFactory(skuDTO: CreateSkuDTO): SKU{
            return SKU(
                id = UUID.randomUUID().toString(),
                gem = skuDTO.gem,
                coin = skuDTO.coin,
                price = skuDTO.price
            )
        }
    }
}