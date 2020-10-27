package org.csuf.cspc411

// Name: Jacob Powell
// CWID: 889234415

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.utils.io.*
import org.csuf.cspc411.Dao.Claim.Claim
import org.csuf.cspc411.Dao.Claim.ClaimDao
import java.lang.reflect.Type

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
///Library/Java/JavaVirtualMachines/jdk1.8.0_261.jdk/Contents/Home/bin/java
fun Application.module(testing: Boolean = false) {
    //extention
    //@annotation
    //routing constructor only takes one parameter, which is a lambda function
    //DSL - Domain Specific Language
    val server = embeddedServer(Netty, 8010) {
        routing {
            this.post("/ClaimService/add") {
                val contType = call.request.contentType()
                val data = call.request.receiveChannel()
                val dataLength = data.availableForRead
                var output = ByteArray(dataLength)
                data.readAvailable(output)
                //read data recieve data then put into buffer
                val claimString = String(output)

                val claimType : Type = object : TypeToken<Claim>(){}.type
                var claimObject : Claim = Gson().fromJson(claimString, claimType)

                ClaimDao().addClaim(claimObject)
                println("HTTP message is using POST method with /post $contType \n $claimString")
                call.respondText(
                    "The POST request was successfully processed",
                    status = HttpStatusCode.OK,
                    contentType = ContentType.Text.Plain
                )
            }

            this.get("/ClaimService/getAll") {
                val claimsList = ClaimDao().getAll()
                println("The number of claims: ${claimsList.size}")
                call.respondText(
                    Gson().toJson(claimsList),
                    status = HttpStatusCode.OK,
                    contentType = ContentType.Application.Json
                )
            }
        }
    }
    server.start(wait = true)
}


