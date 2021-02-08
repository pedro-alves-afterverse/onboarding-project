package com.playkids.onboarding.postgresql.extensions

import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.QueryResult
import com.github.jasync.sql.db.RowData
import com.github.jasync.sql.db.SuspendingConnection
import com.github.jasync.sql.db.util.map
import kotlinx.coroutines.future.await
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime
import java.time.Instant
import java.util.*
import java.util.concurrent.CompletableFuture

suspend fun CompletableFuture<QueryResult>.awaitRows() = map { it.rows }.await()!!

suspend fun <T> CompletableFuture<QueryResult>.awaitMapping(transformation: suspend (RowData) -> T) =
    awaitRows().map { transformation(it) }

fun Connection.query(statement: String, vararg objects: Any?) =
    sendPreparedStatement(statement, listOf(*objects))

suspend fun SuspendingConnection.query(statement: String, vararg objects: Any?) =
    sendPreparedStatement(statement, listOf(*objects))

fun Connection.query(query: String) =
    sendPreparedStatement(query)

fun RowData.getCurrency(column: String) =
    getString(column)?.let { Currency.getInstance(it) }

inline fun <reified T : Enum<T>> RowData.getEnum(value: String) = getString(value)?.let {
    try {
        enumValueOf<T>(it)
    } catch (e: Exception) {
        throw IllegalArgumentException("$value has an invalid entry for ${T::class.simpleName} ", e)
    }
}

fun LocalDateTime.toInstant(dateTimeZone: DateTimeZone = DateTimeZone.UTC): Instant = Instant.ofEpochMilli(
    toDateTime(dateTimeZone).millis
)

fun RowData.getInstant(column: String, dateTimeZone: DateTimeZone = DateTimeZone.UTC): Instant? =
    getDate(column)?.toInstant(dateTimeZone)

fun <T> RowData.getArray(column: String) = getAs<ArrayList<T>>(column)
inline fun <reified T> RowData.getList(value: String) = getArray<T>(value).toList()
inline fun <reified T> RowData.getSet(value: String) = getArray<T>(value).toSet()

inline fun <reified T : Enum<T>> RowData.enum(field: String) =
    getEnum<T>(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.string(field: String) =
    getString(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.stringOrNull(field: String) =
    getString(field)

fun RowData.int(field: String) =
    getInt(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.long(field: String) =
    getLong(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.double(field: String) =
    getDouble(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.instant(field: String) =
    getInstant(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.boolean(field: String) =
    getBoolean(field) ?: throw IllegalArgumentException("Field $field cannot be null")

fun RowData.uuid(field: String) = try {
    getAs<UUID>(field)
} catch (e: Exception) {
    throw IllegalArgumentException("Field $field is not a valid UUID ", e)
}
