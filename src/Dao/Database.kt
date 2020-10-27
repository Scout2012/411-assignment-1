package org.csuf.cspc411.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

class Database constructor (var dbName : String = "") {

    init {
        // Create the database, create tables and keeps the db connection
        dbName = "C:\\Users\\Scout 2012\\Desktop\\testDB.db"
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        val sqlStmt = "create table if not exists claim (id text, title text, date text, isSolved boolean)"
        dbConn.exec(sqlStmt)
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName))
        dbConn.open()
        return dbConn
    }

    // single object pattern
    companion object {
        private var dbObj : Database? = null

        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database()
            }
            return dbObj
        }
    }
}

fun main() {

}