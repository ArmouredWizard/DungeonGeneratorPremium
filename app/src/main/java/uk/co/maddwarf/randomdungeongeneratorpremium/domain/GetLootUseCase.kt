package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Cash
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Loot

class GetLootUseCase {

    fun buildLoot(type: String, level: Int, context: Context): Loot {
        var loot = Loot(name = "$type Loot")
        var cashTableList = listOf<Cash>()

        val cashLevel = when (level) {
            in 1..4 -> "4"
            in 5..10 -> "10"
            in 11..16 -> "16"
            in 17..20 -> "20"
            else -> "0"
        }

        //todo Build Lootz
        when (type) {
            "Individual" -> {
                val cashJsonString: String =
                    FileHelper().readAsset(context = context, fileName = "cash.json")
                cashTableList = makeCashTables(cashJsonString, cashLevel)
            }

            "Hoard" -> {
                val cashJsonString: String =
                    FileHelper().readAsset(
                        context = context,
                        fileName = "cashhoard.json"
                    )
                cashTableList = makeCashTables(cashJsonString, cashLevel)
//todo ADD ITEMS, ART, ETC
            }

            else -> {
                loot = Loot(name = "UNKNOWN LOOT")
            }
        }


        val cashTable: Cash = chooseFromCashTablesList(cashTableList)
        val cash = buildCash(cashTable)




        loot = loot.copy(coins = cash)

        return loot
    }//end Build Loot

    private fun makeCashTables(cashJsonString: String, cashLevel: String): List<Cash> {

        val cashJsonObject = JSONObject(cashJsonString)
        val cashArray = cashJsonObject.getJSONArray(cashLevel)

        val thisCashTableList = mutableListOf<Cash>()
        for (i in 0 until cashArray.length()) {
            val c = cashArray.getJSONObject(i)
            val pp = c.getInt("pp")
            val gp = c.getInt("gp")
            val ep = c.getInt("ep")
            val sp = c.getInt("sp")
            val cp = c.getInt("cp")
            thisCashTableList.add(Cash(pp = pp, gp = gp, ep = ep, sp = sp, cp = cp))
        }

        return thisCashTableList
        // return cash
    }//end make Cash Tables

    private fun chooseFromCashTablesList(cashTableList: List<Cash>): Cash {

        var totalWeight = 0
        for (aCash in cashTableList) {
            totalWeight += aCash.weight
        }

        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aCash in cashTableList) {
            val thisWeight: Int = aCash.weight
            testWeight += thisWeight
            if (roll <= testWeight) {
                return aCash
            }
        }
        return Cash()
    }

    private fun buildCash(cashTable:Cash):Cash{

        val pp = cashTable.pp * (1..6).random()
        val gp = cashTable.gp * (1..6).random()
        val ep = cashTable.ep * (1..6).random()
        val sp = cashTable.sp * (1..6).random()
        val cp = cashTable.cp * (1..6).random()


        return Cash(pp = pp, gp = gp, ep = ep, sp = sp, cp = cp)
    }


}//end Gey Loot Use Case