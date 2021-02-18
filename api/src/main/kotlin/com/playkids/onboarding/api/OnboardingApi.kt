package com.playkids.onboarding.api

import com.fasterxml.jackson.databind.SerializationFeature
import com.playkids.onboarding.api.extensions.exceptions
import com.playkids.onboarding.api.extensions.logger
import com.playkids.onboarding.api.route.itemRouting
import com.playkids.onboarding.api.route.profileRouting
import com.playkids.onboarding.api.route.skuRouting
import com.playkids.onboarding.core.service.ItemService
import com.playkids.onboarding.core.service.ProfileService
import com.playkids.onboarding.core.service.SKUService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class OnboardingApi(
    private val serverPort: Int,
    private val itemService: ItemService,
    private val skuService: SKUService,
    private val profileService: ProfileService,
){
    fun start() {
        embeddedServer(Netty, serverPort) {

            install(StatusPages) {
                exceptions(logger)
            }

            install(ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
            }

            install(CORS){
                anyHost()
                method(HttpMethod.Get)
                method(HttpMethod.Post)
                method(HttpMethod.Patch)
                allowNonSimpleContentTypes = true
            }

            routing {
                get("/health") {
                    call.respondText("HELLO WORLD!", contentType = ContentType.Text.Plain)
                }
                route("/api") {
                    itemRouting(itemService)
                    skuRouting(skuService)
                    profileRouting(profileService)
                }

            }
        }.start(wait = true)
    }

    companion object {
        internal val logger = logger<OnboardingApi>()
    }
}