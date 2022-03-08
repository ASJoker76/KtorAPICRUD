package com.example.database

import org.jetbrains.exposed.sql.Table

object AccountTable : Table() {
    val serialnumber = varchar("serialnumber", 255).primaryKey()
    val username = varchar("name", 255)
    val password = varchar("dist", 255)
}