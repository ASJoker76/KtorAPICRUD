package alfianyusufabdullah.databaseservice.repository

import alfianyusufabdullah.databaseservice.database.InventoryTable
import alfianyusufabdullah.databaseservice.database.dbQuery
import alfianyusufabdullah.databaseservice.model.Inventory
import com.example.database.AccountTable
import com.example.model.Account
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class DatabaseRepository {

    //inventory

    suspend fun getAllInventory(): HashMap<String, Inventory> {
        val inventory = hashMapOf<String, Inventory>()
        dbQuery {
            InventoryTable.selectAll().forEach {
                inventory[it[InventoryTable.serialnumber]] = Inventory(
                    serialNumber = it[InventoryTable.serialnumber],
                    name = it[InventoryTable.name],
                    distribution = it[InventoryTable.distribution],
                    price = it[InventoryTable.price].toInt()
                )
            }
        }
        return inventory
    }

    suspend fun insertInventory(inventory: Inventory): Boolean {
        return dbQuery {
            try {
                InventoryTable.insert {
                    it[serialnumber] = inventory.serialNumber
                    it[name] = inventory.name
                    it[distribution] = inventory.distribution
                    it[price] = inventory.price
                }
                true
            } catch (e: JdbcSQLIntegrityConstraintViolationException) {
                println(e.localizedMessage)
                false
            } catch (e: ExposedSQLException) {
                println(e.localizedMessage)
                false
            }
        }
    }

    suspend fun deleteInventory(id: String): Int {
        return dbQuery {
            InventoryTable.deleteWhere { InventoryTable.serialnumber eq id }
        }
    }

    suspend fun updateInventory(inventory: Inventory): Int {
        return dbQuery {
            InventoryTable.update({ InventoryTable.serialnumber eq inventory.serialNumber }) {
                it[name] = inventory.name
                it[distribution] = inventory.distribution
                it[price] = inventory.price
            }
        }
    }

    //account
    suspend fun getAllAccount(): HashMap<String, Account> {
        val account = hashMapOf<String, Account>()
        dbQuery {
            AccountTable.selectAll().forEach {
                account[it[AccountTable.serialnumber]] = Account(
                    serialNumber = it[AccountTable.serialnumber],
                    username = it[AccountTable.username],
                    password = it[AccountTable.password]
                )
            }
        }
        return account
    }

    suspend fun insertAccount(account: Account): Boolean {
        return dbQuery {
            try {
                AccountTable.insert {
                    it[serialnumber] = account.serialNumber
                    it[username] = account.username
                    it[password] = account.password
                }
                true
            } catch (e: JdbcSQLIntegrityConstraintViolationException) {
                println(e.localizedMessage)
                false
            } catch (e: ExposedSQLException) {
                println(e.localizedMessage)
                false
            }
        }
    }
}