package alfianyusufabdullah.databaseservice.rest

import alfianyusufabdullah.databaseservice.model.Inventory
import alfianyusufabdullah.databaseservice.model.Response
import alfianyusufabdullah.databaseservice.repository.DatabaseRepository
import com.example.model.Account
import io.ktor.application.call
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*

fun Route.widget(databaseRepository: DatabaseRepository) {

    route("/inventory") {

        get {
            val result = databaseRepository.getAllInventory()
            if (result.isEmpty()) {
                call.respond(Response("success", "Data Inventory kosong"))
            } else {
                call.respond(Response("success", "Jumlah inventory ${result.size}", result))
            }
        }

        get("/insert") {
            val name = call.request.queryParameters["name"] ?: "samsung"
            val distribution = call.request.queryParameters["dist"] ?: "samsung electronic"
            val price = call.request.queryParameters["price"] ?: "0"
            val serial = call.request.queryParameters["serial"] ?: "0"

            val inventory =
                Inventory(name = name, distribution = distribution, price = price.toInt(), serialNumber = serial)
            val result = databaseRepository.insertInventory(inventory)

            if (result) {
                call.respond(Response("success", "${inventory.name} berhasil ditambahkan", inventory))
            } else {
                call.respond(Response("failed", "${inventory.name} tidak dapat ditambahkan"))
            }
        }

        get("/update") {
            val name = call.request.queryParameters["name"] ?: "samsung"
            val distribution = call.request.queryParameters["dist"] ?: "samsung electronic"
            val price = call.request.queryParameters["price"] ?: "0"
            val serial = call.request.queryParameters["serial"] ?: "0"

            val inventory =
                Inventory(name = name, distribution = distribution, price = price.toInt(), serialNumber = serial)
            val result = databaseRepository.updateInventory(inventory)

            if (result > 0) {
                call.respond(
                    Response(
                        "success",
                        "data dengan serial number ${inventory.serialNumber} berhasil diupdate",
                        inventory
                    )
                )
            } else {
                call.respond(Response("failed", "tidak dapat melakukan update data"))
            }
        }

        get("/delete") {
            val serial = call.request.queryParameters["serial"] ?: "0"
            val result = databaseRepository.deleteInventory(serial)

            if (result > 0) {
                call.respond(Response("success", "data dengan serial number $serial berhasil dihapus"))
            } else {
                call.respond(Response("failed", "tidak dapat menghapus data"))
            }
        }
    }

    route("/account"){
        get{
            val result = databaseRepository.getAllAccount()
            if (result.isEmpty()) {
                call.respond(Response("success", "Data Account kosong"))
            } else {
                call.respond(Response("success", "Jumlah account ${result.size}", result))
            }
        }
        post("/insert"){
            val randomNumber = (100..200).random()
            val serial = randomNumber

            val parameters = call.receiveParameters()

            val username = parameters["username"]
            val password = parameters["password"]

            val account =
                Account(username = username!!, password = password!!, serialNumber = serial.toString()!!)
            val result = databaseRepository.insertAccount(account)

            if (result) {
                call.respond(Response("success", "${account.username} berhasil ditambahkan", account))
            } else {
                call.respond(Response("failed", "${account.username} tidak dapat ditambahkan"))
            }
        }
    }
}