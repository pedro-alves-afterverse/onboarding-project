package com.playkids.onboarding.api.response

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.OutgoingContent

/**
 * Created by Diego Rocha (diego.rocha@playkids.com)
 */
data class ErrorResponse(
    override val status: HttpStatusCode,
    val description: String
) : OutgoingContent.ByteArrayContent() {

    override fun bytes(): ByteArray =
        """
      |{
      |  "statusCode": ${status.value},
      |  "status": "${status.description}",
      |  "description": "$description"
      |}
    """.trimMargin().toByteArray(Charsets.UTF_8)
}
