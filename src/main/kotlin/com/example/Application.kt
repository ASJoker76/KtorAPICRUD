package com.example

import alfianyusufabdullah.databaseservice.repository.DatabaseRepository
import alfianyusufabdullah.databaseservice.database.DatabaseConfiguration
import alfianyusufabdullah.databaseservice.database.InventoryTable
import alfianyusufabdullah.databaseservice.rest.widget
import com.example.database.AccountTable
import io.ktor.application.*
import com.example.plugins.*

import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction


fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {

    Database.connect(DatabaseConfiguration.hikariConfigDataSource())
    transaction {
        create(InventoryTable)
        create(AccountTable)
    }

    install(ContentNegotiation) {
        gson {
            setPrettyPrinting()
        }
    }

    install(Routing) {
        widget(DatabaseRepository())
    }
//    configureRouting()
//    configureSockets()
//    configureSerialization()
//    configureMonitoring()
//    configureSecurity()
}
