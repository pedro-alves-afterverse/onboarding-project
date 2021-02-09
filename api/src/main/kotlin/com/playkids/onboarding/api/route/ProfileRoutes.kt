package com.playkids.onboarding.api.route

import com.movile.kotlin.commons.ktor.patch
import com.movile.kotlin.commons.ktor.post
import com.movile.kotlin.commons.serialization.toJson
import com.playkids.onboarding.api.dto.AddItemDTO
import com.playkids.onboarding.api.dto.AddSkuDTO
import com.playkids.onboarding.api.dto.BuyItemDTO
import com.playkids.onboarding.api.sqs.SQSEventEmitter
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.service.ProfileService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*

fun Route.profileRouting(
    profileService: ProfileService,
    sqsEventEmitter: SQSEventEmitter
) {

    fun PipelineContext<*, ApplicationCall>.id(): String {
        return call.parameters["id"] ?: throw IllegalArgumentException("an id must be provided")
    }

    route("/profile") {
        get("{id}") {
            val id = id()

            val profile = profileService.find(id) ?: throw NotFoundException("id doesn't exists")

            call.respond(profile)
        }
        post<Profile> { profile ->
            profileService.create(profile)

            val sqsAttributes = mapOf(
                "entity" to "Profile",
                "operation" to "insert"
            )

            sqsEventEmitter.sendEvent(profile.toJson().get(), sqsAttributes).join()

            call.respondText("Profile Created", status = HttpStatusCode.OK)
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

                profileService.buyItem(id, item.id, item.category, item.currency)

                call.respondText("Item bought successfully", status = HttpStatusCode.OK)

            }
        }
    }
}