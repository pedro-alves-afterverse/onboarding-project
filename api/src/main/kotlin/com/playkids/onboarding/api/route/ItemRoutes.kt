package com.playkids.onboarding.api.route

import com.movile.kotlin.commons.ktor.post
import com.playkids.onboarding.api.extensions.logger
import com.playkids.onboarding.core.dto.CreateItemDTO
import com.playkids.onboarding.core.model.Item
import com.playkids.onboarding.core.service.ItemService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.util.pipeline.*
import kotlin.IllegalArgumentException
import kotlin.math.log

fun Route.itemRouting(itemService: ItemService) {

    fun PipelineContext<*, ApplicationCall>.category(): String {
        return call.parameters["category"] ?: throw IllegalArgumentException("a category must be provided")
    }

    fun PipelineContext<*, ApplicationCall>.id(): String {
        return call.parameters["id"] ?: throw IllegalArgumentException("an id must be provided")
    }

    route("/item") {
        get("{category}/{id}") {
            val id = id()
            val category = category()

            val item = itemService.find(category, id) ?: throw NotFoundException("Item not found")

            call.respond(item)

        }
        get("{category}") {
            val category = category()

            val item = itemService.findAllByCategory(category) ?: throw NotFoundException("Category doesn't exists")

            call.respond(item)
        }
        get("/{category}/user/{id}") {
            val id = id()
            val category = category()

            val items = itemService.findAllByCategoryForUser(category, id) ?: throw NotFoundException("No items found")

            call.respond(items)
        }
        post<CreateItemDTO> { itemDTO ->
            val item = itemService.create(itemDTO)

            call.respond(item)
        }
    }
}