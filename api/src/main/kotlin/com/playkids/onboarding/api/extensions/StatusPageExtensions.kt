package com.playkids.onboarding.api.extensions

import com.playkids.onboarding.api.response.ErrorResponse
import com.playkids.onboarding.core.excption.EntityNotFoundException
import com.playkids.onboarding.core.excption.NotEnoughCurrencyException
import com.playkids.onboarding.core.excption.UserHasItemException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*
import org.slf4j.Logger

fun StatusPages.Configuration.exceptions(logger: Logger) {

    exception<NotFoundException> {
        logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)

        call.respond(HttpStatusCode.NotFound, ErrorResponse(HttpStatusCode.NotFound, it.message ?: ""))
    }

    exception<EntityNotFoundException> {
        logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)

        call.respond(HttpStatusCode.NotFound, ErrorResponse(HttpStatusCode.NotFound, it.message ?: ""))
    }

    exception<NotEnoughCurrencyException> {
        logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)

        call.respond(HttpStatusCode.PreconditionFailed, ErrorResponse(HttpStatusCode.PreconditionFailed, it.message ?: ""))
    }

    exception<UserHasItemException> {
        logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)

        call.respond(HttpStatusCode.Conflict, ErrorResponse(HttpStatusCode.Conflict, it.message ?: ""))
    }

    exception<IllegalArgumentException> {
        logger.warn("Failed to process request {}: {}", call.request.toLogString(), it.message, it)

        call.respond(HttpStatusCode.BadRequest, ErrorResponse(HttpStatusCode.BadRequest, it.message ?: ""))
    }

    exception<Throwable> {
        logger.error("Failed to process request {}: {}", call.request.toLogString(), it.message, it)

        call.respond(
            HttpStatusCode.InternalServerError,
            ErrorResponse(HttpStatusCode.InternalServerError, it.message ?: "")
        )
    }
}
