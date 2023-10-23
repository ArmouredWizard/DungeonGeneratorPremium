package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Art
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Cash
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Gem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.ItemTableEntry
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Loot
import uk.co.maddwarf.randomdungeongeneratorpremium.model.MundaneItem

class GetLootUseCase {

    fun buildLoot(type: String, level: Int, context: Context): Loot {
        var loot = Loot(name = "$type Loot")
        var cashTableList = listOf<Cash>()

        val lootLevel = when (level) {
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
                cashTableList = makeCashTables(cashJsonString, lootLevel)
            }

            "Hoard" -> {
                val cashJsonString: String =
                    FileHelper().readAsset(
                        context = context,
                        fileName = "cashhoard.json"
                    )
                cashTableList = makeCashTables(cashJsonString, lootLevel)
//todo ADD ITEMS, ART, ETC

                var itemTable = getItemTable(context = context, lootLevel = lootLevel)
                var itemTableEntry = chooseFromItemTable(itemTable = itemTable)

                loot = rollOnLootTable(itemTableEntry = itemTableEntry, context = context)
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

    private fun buildCash(cashTable: Cash): Cash {

        val pp = cashTable.pp * Dice().roll("1d6")
        val gp = cashTable.gp * (1..6).random()
        val ep = cashTable.ep * (1..6).random()
        val sp = cashTable.sp * (1..6).random()
        val cp = cashTable.cp * (1..6).random()


        return Cash(pp = pp, gp = gp, ep = ep, sp = sp, cp = cp)
    }

    private fun getItemTable(context: Context, lootLevel: String): List<ItemTableEntry> {
        val itemJsonString =
            FileHelper().readAsset(context = context, fileName = "lootItems.json")

        val itemJsonObject = JSONObject(itemJsonString)
        val itemArray = itemJsonObject.getJSONArray(lootLevel)

        val thisItemTableList = mutableListOf<ItemTableEntry>()

        for (itemEntryIndex in 0 until itemArray.length()) {
            val i = itemArray.getJSONObject(itemEntryIndex)
            val numberOfArtItems = i.getString("artnumber")
            val artOrGem = i.getString("artorgem")
            val artValue = i.getInt("artvalue")
            val cr = i.getInt("cr")
            val numberOfMagicItemsFromTableOne = i.getString("magicnumber")
            val numberOfMagicItemsFromTableTwo = i.getString("magicnumber2")
            val magicItemTableOne = i.getString("magictable")
            val magicItemTableTwo = i.getString("magictable2")
            val weight = i.getInt("weight")
            thisItemTableList.add(
                ItemTableEntry(
                    numberOfMundaneItems = numberOfArtItems,
                    mundaneItemType = artOrGem,
                    mundaneValue = artValue,
                    cr = cr,
                    numberOfMagicItemsFromTableOne = numberOfMagicItemsFromTableOne,
                    numberOfMagicItemsFromTableTwo = numberOfMagicItemsFromTableTwo,
                    magicItemTableOne = magicItemTableOne,
                    magicItemTableTwo = magicItemTableTwo,
                    weight = weight
                )
            )
        }

        return thisItemTableList
    }//end get item table

    private fun chooseFromItemTable(itemTable: List<ItemTableEntry>): ItemTableEntry {

        var totalWeight = 0
        for (anEntry in itemTable) {
            totalWeight += anEntry.weight
        }

        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (anEntry in itemTable) {
            val thisWeight: Int = anEntry.weight
            testWeight += thisWeight
            if (roll <= testWeight) {
                return anEntry
            }
        }
        return ItemTableEntry()
    }

    private fun rollOnLootTable(itemTableEntry: ItemTableEntry, context: Context): Loot {

        //todo roll on loot table
        //art/gem
        val artList = mutableListOf<Art>()
        val gemList = mutableListOf<Gem>()
        val numberOfMundaneItems = Dice().roll(itemTableEntry.numberOfMundaneItems)
        for (i in 1..numberOfMundaneItems) {
            when (itemTableEntry.mundaneItemType) {
                "GEM" -> gemList.add(
                    makeGem(
                        gemValue = itemTableEntry.mundaneValue,
                        context = context
                    )
                )

                "ART" -> artList.add(
                    makeArt(
                        artValue = itemTableEntry.mundaneValue,
                        context = context
                    )
                )
            }
        }

        //magic Items


        val newLoot = Loot(name = "A Loot", artList = artList, gemList = gemList)
        return newLoot
    }//end roll on loot table

    private fun makeGem(context: Context, gemValue: Int): Gem {

        val gemJsonString =
            FileHelper().readAsset(context = context, fileName = "gems.json")

        val gemJsonObject = JSONObject(gemJsonString)
        val gemArray = gemJsonObject.getJSONArray(gemValue.toString())

        val gemList = mutableListOf<Gem>()
        var thisGem: Gem

        for (i in 0 until gemArray.length()) {
            val c = gemArray.getJSONObject(i)
            val name = c.getString("name")
//            val weight = c.getString("Weight").toInt()
            val description = c.getString("description")
            thisGem = Gem(name = name, description = description, value = gemValue)
            gemList.add(thisGem)
        }

        val newGem = gemList.random()
        return newGem

    }//end Make Gem

    private fun makeArt(context: Context, artValue: Int): Art {

        val artJsonString =
            FileHelper().readAsset(context = context, fileName = "arts.json")

        val artJsonObject = JSONObject(artJsonString)
        val artArray = artJsonObject.getJSONArray(artValue.toString())

        val artList = mutableListOf<Art>()
        var thisArt: Art

        for (i in 0 until artArray.length()) {
            val c = artArray.getJSONObject(i)
            val name = c.getString("name")
            val value = c.getString("value").toInt()
            val description = c.getString("description")
            thisArt = Art(name = name, description = description, value = value)
            artList.add(thisArt)
        }

        val newArt = artList.random()
        return newArt

    }//end Make Art


}//end Gey Loot Use Case