package uk.co.maddwarf.randomdungeongeneratorpremium.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Art
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Cash
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Gem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.ItemTableEntry

object LootRepository {

    fun getCashTableList(context: Context, table: String, lootLevel: String):List<Cash>{
        val cashJsonString: String =
            FileHelper().readAsset(context = context, fileName = "$table.json")
        val cashTableList = makeCashTables(cashJsonString, lootLevel)

        return cashTableList
    }

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

     fun getItemTableList(context: Context, lootLevel: String): List<ItemTableEntry> {
         if (lootLevel == "0"){return listOf()}
        val itemJsonString =
            FileHelper().readAsset(context = context, fileName = "lootItems.json")

        val itemJsonObject = JSONObject(itemJsonString)
        val itemArray = itemJsonObject.getJSONArray(lootLevel)

        val thisItemTableList = mutableListOf<ItemTableEntry>()

         //todo GSON/Moshi
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

    fun getGemList(context: Context, gemValue: Int):List<Gem> {
        val gemJsonString =
            FileHelper().readAsset(context = context, fileName = "gems.json")

        val gemJsonObject = JSONObject(gemJsonString)
        val gemArray = gemJsonObject.getJSONArray(gemValue.toString())

        val gemList = mutableListOf<Gem>()
        var thisGem: Gem

        for (i in 0 until gemArray.length()) {
            val c = gemArray.getJSONObject(i)
            val typeToken = object : TypeToken<Gem>() {}.type
            thisGem = Gson().fromJson(c.toString(), typeToken)
            gemList.add(thisGem)
        }
        return gemList
    }//end getGemList

    fun getArtList(context: Context, artValue: Int):List<Art>{
        val artJsonString =
            FileHelper().readAsset(context = context, fileName = "arts.json")

        val artJsonObject = JSONObject(artJsonString)
        val artArray = artJsonObject.getJSONArray(artValue.toString())

        val artList = mutableListOf<Art>()
        var thisArt: Art

        for (i in 0 until artArray.length()) {
            val c = artArray.getJSONObject(i)
            val typeToken = object : TypeToken<Art>() {}.type
            thisArt = Gson().fromJson(c.toString(), typeToken)
            artList.add(thisArt)
        }
        return artList
    }

}