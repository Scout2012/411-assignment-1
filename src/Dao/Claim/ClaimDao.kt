package org.csuf.cspc411.Dao.Claim

import org.csuf.cspc411.Dao.Dao
import org.csuf.cspc411.Dao.Database
import java.util.UUID

class ClaimDao : Dao() {

    fun addClaim(claim: Claim) {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()
        claim.id = UUID.randomUUID().toString();
        // 2. prepare the sql statement
        sqlStmt = "insert into claim (id, title, date, isSolved) values ('${claim.id}', '${claim.title}', '${claim.date}', '${claim.isSolved}')"

        // 3. submit the sql statement
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        // 1. Get db connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare the sql statement
        sqlStmt = "select id, title, date, isSolved from claim"

        // 3. submit the sql statement
        var personList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // 4. Convert into Kotlin object format
        while (st?.step()!!) {
            println(st)
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnString(2).toBoolean()

            personList.add(Claim(id, title, date, isSolved))
        }
        return personList
    }
}