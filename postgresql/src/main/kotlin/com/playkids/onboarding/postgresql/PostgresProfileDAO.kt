package com.playkids.onboarding.postgresql

import com.github.jasync.sql.db.Connection
import com.playkids.onboarding.core.model.ItemKey
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.persistence.ProfileDAO
import com.playkids.onboarding.core.util.Currencies
import com.playkids.onboarding.core.util.camelToSnakeCase
import com.playkids.onboarding.postgresql.extensions.query
import kotlinx.coroutines.future.await
import javax.naming.OperationNotSupportedException

class PostgresProfileDAO(private val db: Connection): ProfileDAO {
    override suspend fun create(profile: Profile) {
        db.query(
            INSERT_QUERY,
            profile.id,
            profile.username,
            profile.coin,
            profile.gem,
            profile.moneySpent,
            profile.region
        ).await()
    }

    override suspend fun updateCurrency(profileId: ProfileId, currency: Currencies, value: Number) {
        db.query(
            UPDATE_CURRENCY_QUERY.replace("COLUMN", currency.toString().camelToSnakeCase()),
            value,
            profileId
        ).await()
    }

    override suspend fun getProfileItems(id: ProfileId): List<ItemKey>? {
        throw OperationNotSupportedException("Operation not supported")
    }

    override suspend fun find(id: ProfileId): Profile? {
        throw OperationNotSupportedException("Operation not supported")
    }

    override suspend fun getItemsAndCurrency(id: ProfileId, currency: Currencies): Pair<List<ItemKey>, Int>? {
        throw OperationNotSupportedException("Operation not supported")
    }


    override suspend fun addItem(profileId: ProfileId, item: List<String>) {
        throw OperationNotSupportedException("Operation not supported")
    }


    companion object {
        private const val ID = "id"
        private const val USERNAME = "username"
        private const val COIN = "coin"
        private const val GEM = "gem"
        private const val MONEY_SPENT = "money_spent"
        private const val REGION = "region"

        private const val TABLE_NAME = "profiles"

        private val PROJECTION = """
            $ID,
            $USERNAME,
            $COIN,
            $GEM,
            $MONEY_SPENT,
            $REGION
        """.trimIndent()

        val INSERT_QUERY = """
            INSERT INTO $TABLE_NAME ($PROJECTION)
            VALUES (?, ?, ?, ?, ?, ?)
        """.trimIndent()

        val UPDATE_CURRENCY_QUERY = """
            UPDATE $TABLE_NAME
            SET COLUMN = COLUMN + ?
            WHERE $ID = ?
        """.trimIndent()
    }
}