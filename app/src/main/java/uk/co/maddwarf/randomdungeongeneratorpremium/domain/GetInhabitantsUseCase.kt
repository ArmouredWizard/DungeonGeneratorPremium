package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class GetInhabitantsUseCase (){
    fun getInhabitantsCategories(context: Context, all: Boolean): List<String> {
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
        calledThemes.sort()
        if (all){
            calledThemes.add(0, "RANDOM")
            calledThemes.add(0,"ALL")
        }
        return calledThemes


    }


}