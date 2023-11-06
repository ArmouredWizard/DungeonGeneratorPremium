package uk.co.maddwarf.randomdungeongeneratorpremium.repository

import android.content.Context
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.Dice

object DescriptionsRepository {

    fun getPotionDescription(context: Context): String {
        val description: String
        val vowels = "aeiou"

        val colour: String = getColour(context = context)
        val fizz: String = getFizz(context = context)
        var a = "A "

        if (vowels.indexOf(colour[0].lowercaseChar()) != -1) {
            // Start char is vowel
            a = "An "
        }
        description = "$a$colour, $fizz liquid."
        return description
    }//end get potion description

    private fun getFizz(context: Context): String {
        val fizzList = FileHelper().getList(context = context, file = "fizz.json", table = "fizz")
        return fizzList.random()
    }//end get fizz

    private fun getColour(context: Context): String {
        val colourList =
            FileHelper().getList(context = context, file = "colour.json", table = "colours")
        return colourList.random()
    }//end getColour

    fun getCantrip(context: Context): String {
        val cantripList =
            FileHelper().getList(context = context, file = "cantrips.json", table = "cantrips")
        return cantripList.random()
    }//end getCantrip

    fun getSpellByLevel(context: Context, level: String): String {
        val spellList = FileHelper().getList(context = context, file = "spells.json", table = level)
        return spellList.random()
    }//end getCantrip

//todo change this?
    fun getAmmoDescription(context: Context): String {
        val ammoList = mutableListOf<String>()
        var thisAmmo: String
        val ammoJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "ammo.json"
        )
        val ammoJsonObject = JSONObject(ammoJsonString)
        val ammoArray = ammoJsonObject.getJSONArray("ammo")
        for (i in 0 until ammoArray.length()) {
            val c = ammoArray.getJSONObject(i)
            val thisAmmoAmount = Dice().roll(c.getString("amount"))
            val thisAmmoType = c.getString("name")
            thisAmmo = "$thisAmmoAmount $thisAmmoType"
            ammoList.add(thisAmmo)
        }
        return ammoList.random()
    }//end getCantrip


}//end descriptions repo