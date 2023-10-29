package uk.co.maddwarf.randomdungeongeneratorpremium.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Magic
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monster

object MagicItemRepository {

    fun getMagicItemsList(context: Context, table: String):List<Magic> {
        val magicItemsList = mutableListOf<Magic>()
        var thisMagicItem: Magic

        val magicItemJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "magicitems.json"
        )

        val magicItemJsonObject = JSONObject(magicItemJsonString)
        val magicItemArray = magicItemJsonObject.getJSONArray(table)

        for (i in 0 until magicItemArray.length()) {
            val c = magicItemArray.getJSONObject(i)
            val typeToken = object : TypeToken<Magic>() {}.type
            thisMagicItem = Gson().fromJson(c.toString(), typeToken)
            magicItemsList.add(thisMagicItem)
        }

        return magicItemsList
    }



}