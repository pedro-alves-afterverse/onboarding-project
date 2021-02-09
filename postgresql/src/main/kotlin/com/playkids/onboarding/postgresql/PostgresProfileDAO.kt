package com.playkids.onboarding.postgresql

import com.github.jasync.sql.db.Connection
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.persistence.ProfileDAO
import com.playkids.onboarding.core.util.ChooseValue
import com.playkids.onboarding.core.util.ItemsCurrency
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

    override suspend fun updateCurrency(
        profileId: ProfileId,
        operation: String,
        currency: String,
        chooseValue: ChooseValue
    ) {
//        db.query(
//            UPDATE_MONEY_QUERY,
//            coin,
//            gem,
//            moneySpent,
//            profileId
//        ).await()
    }

    override suspend fun find(id: ProfileId): Profile? {
        throw OperationNotSupportedException("Operação não suportada")
    }

    override suspend fun getItemsAndCurrency(
        id: ProfileId,
        projection: Map<String, String>,
        currency: String
    ): ItemsCurrency? {
        throw OperationNotSupportedException("Operação não suportada")
    }

    override suspend fun addItem(profileId: ProfileId, item: List<String>) {
        throw OperationNotSupportedException("Operação não suportada")
    }

    override suspend fun getProfileItems(id: ProfileId, projection: Map<String, String>): List<String>? {
        throw OperationNotSupportedException("Operação não suportada")
    }

    suspend fun insert(profile: Profile) {
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

    suspend fun update(profileId: ProfileId, coin: Int, gem: Int, moneySpent: Float) {
        db.query(
            UPDATE_MONEY_QUERY,
            coin,
            gem,
            moneySpent,
            profileId
        ).await()
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

        val UPDATE_MONEY_QUERY = """
            UPDATE $TABLE_NAME
            SET $COIN = $COIN + ?, $GEM = $GEM + ?, $MONEY_SPENT = $MONEY_SPENT + ?
            WHERE $ID = ?
        """.trimIndent()
    }
}