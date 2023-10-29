package uk.co.maddwarf.randomdungeongeneratorpremium.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Magic

object DescriptionsRepository {
    fun getPotionDescription(context: Context): String {
        val description:String
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
        val fizzList = mutableListOf<String>()
        var thisFizz: String
        val coloursJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "fizz.json"
        )
        val coloursJsonObject = JSONObject(coloursJsonString)
        val coloursArray = coloursJsonObject.getJSONArray("fizz")
        for (i in 0 until coloursArray.length()) {
            val c = coloursArray.getJSONObject(i)
            thisFizz = c.getString("name")
            fizzList.add(thisFizz)
        }
        return fizzList.random()
    }//end get fizz

    private fun getColour(context: Context): String {
        val colourList = mutableListOf<String>()
        var thisColour: String
        val coloursJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "colour.json"
        )
        val coloursJsonObject = JSONObject(coloursJsonString)
        val coloursArray = coloursJsonObject.getJSONArray("colours")
        for (i in 0 until coloursArray.length()) {
            val c = coloursArray.getJSONObject(i)
            thisColour = c.getString("name")
            colourList.add(thisColour)
        }
        return colourList.random()
    }//end getColour

       fun getCantrip(context: Context): String {
        val cantripList = mutableListOf<String>()
        var thisCantrip: String
        val cantripJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "cantrips.json"
        )
        val cantripJsonObject = JSONObject(cantripJsonString)
        val cantripArray = cantripJsonObject.getJSONArray("cantrips")
        for (i in 0 until cantripArray.length()) {
            val c = cantripArray.getJSONObject(i)
            thisCantrip = c.getString("name")
            cantripList.add(thisCantrip)
        }
        return cantripList.random()
    }//end getCantrip

     fun getSpellByLevel(context: Context, level:String): String {
        val spellList = mutableListOf<String>()
        var thisSpell: String
        val spellsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "spells.json"
        )
        val spellJsonObject = JSONObject(spellsonString)
        val spellArray = spellJsonObject.getJSONArray(level)
        for (i in 0 until spellArray.length()) {
            val c = spellArray.getJSONObject(i)
            thisSpell = c.getString("name")
            spellList.add(thisSpell)
        }
        return spellList.random()
    }//end getCantrip






}//end descriptions repo