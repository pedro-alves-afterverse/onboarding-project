package com.playkids.onboarding.api.route

import com.movile.kotlin.commons.ktor.patch
import com.movile.kotlin.commons.ktor.post
import com.playkids.onboarding.api.dto.AddItemDTO
import com.playkids.onboarding.api.dto.AddSkuDTO
import com.playkids.onboarding.api.dto.BuyItemDTO
import com.playkids.onboarding.core.dto.CreateProfileDTO
import com.playkids.onboarding.core.service.ProfileService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Route.profileRouting(
    profileService: ProfileService,
) {

    fun PipelineContext<*, ApplicationCall>.id(): String {
        return call.parameters["id"] ?: throw IllegalArgumentException("an id must be provided")
    }

    fun PipelineContext<*, ApplicationCall>.category(): String {
        return call.parameters["category"] ?: throw IllegalArgumentException("a category must be provided")
    }

    route("/profile") {
        get("{id}") {
            val id = id()

            val profile = profileService.find(id) ?: throw NotFoundException("id doesn't exists")

            call.respond(profile)
        }
        post<CreateProfileDTO> { profileDTO ->
            val profile = profileService.create(profileDTO)

            call.respond(profile)
        }
        get("/items/{id}/{category}") {
            val id = id()
            val category = category()

            val items = profileService.getProfileItemsByCategory(id, category) ?: throw NotFoundException("profile not found")

            call.respond(items)
        }
        get("/currency/{id}") {
            val id = id()

            val currency = profileService.getProfileCurrency(id) ?: throw NotFoundException("profile not found")

            call.respond(currency)
        }
        route("/add"){
            patch<AddItemDTO>("/item/{id}") {item ->
                val id = id()

                profileService.addItem(id, item.id, item.category)

                call.respondText("Item added to profile", status = HttpStatusCode.OK)
            }
        }

        route("/buy"){
            patch<AddSkuDTO>("/sku/{id}") {sku ->
                val id = id()

                profileService.addSku(id, sku.skuId)

                call.respondText("SKU added to profile", status = HttpStatusCode.OK)
            }

            patch<BuyItemDTO>("/item/{id}") {item ->
                val id = id()

                profileService.buyItem(id, item.id, item.category)

                call.respondText("Item bought successfully", status = HttpStatusCode.OK)

            }
        }
    }
}