package uk.co.maddwarf.randomdungeongeneratorpremium.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monster

object InhabitantsRepository {

    fun getInhabitantsCategoriesList(context: Context): MutableList<String> {
        val jsonHelper = FileHelper()
        val inhabitantJsonString: String =
            jsonHelper.readAsset(context = context, fileName = "inhabitants.json")
        val inhabitantsJsonObject: JsonObject =
            JsonParser.parseString(inhabitantJsonString).asJsonObject!!

        val calledThemes = mutableListOf<String>()
        val theNames = inhabitantsJsonObject.keySet()

        for (element in theNames) {
            calledThemes.add(element)
        }
        return calledThemes
    }//end get inhabitants categories list

    fun getInhabitantsList(inhabitants: String, level: Int, context: Context): List<Monster> {
        val inhabitantsList = mutableListOf<Monster>()
        var thisMonster: Monster

        val monsterJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "inhabitants.json"
        )

        val monsterJsonObject = JSONObject(monsterJsonString)
        val monsterArray = monsterJsonObject.getJSONArray(inhabitants)

        for (i in 0 until monsterArray.length()) {
            val c = monsterArray.getJSONObject(i)
            val typeToken = object : TypeToken<Monster>() {}.type
            thisMonster = Gson().fromJson(c.toString(), typeToken)
            inhabitantsList.add(thisMonster)
        }
        return inhabitantsList
    }//end get inhabitants list


}//end inhabitants repo