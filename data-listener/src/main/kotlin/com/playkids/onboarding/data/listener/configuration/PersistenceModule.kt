package com.playkids.onboarding.data.listener.configuration

import com.github.jasync.sql.db.pool.ConnectionPool
import com.github.jasync.sql.db.postgresql.PostgreSQLConnection
import com.playkids.onboarding.postgresql.PostgresProfileDAO

class PersistenceModule(postgresDB: ConnectionPool<PostgreSQLConnection>) {
    val profileDAO = PostgresProfileDAO(postgresDB)
}