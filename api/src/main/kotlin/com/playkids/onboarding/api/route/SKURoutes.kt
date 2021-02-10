package com.playkids.onboarding.api.route

import com.movile.kotlin.commons.ktor.post
import com.playkids.onboarding.core.dto.CreateSkuDTO
import com.playkids.onboarding.core.model.Item
import com.playkids.onboarding.core.model.SKU
import com.playkids.onboarding.core.service.ItemService
import com.playkids.onboarding.core.service.SKUService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Route.skuRouting(skuService: SKUService) {

    fun PipelineContext<*, ApplicationCall>.id(): String {
        return call.parameters["id"] ?: throw IllegalArgumentException("an id must be provided")
    }

    route("/sku") {
        get("{id}") {
            val id = id()

            val sku = skuService.find(id) ?: throw NotFoundException("id doesn't exists")

            call.respond(sku)
        }
        post<CreateSkuDTO> { skuDTO ->
            val sku = skuService.create(skuDTO)

            call.respond(sku)
        }
    }
}