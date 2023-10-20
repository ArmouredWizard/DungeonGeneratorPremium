package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader

class FileHelper {

    fun readAsset(context: Context, fileName: String): String =
        context
            .assets
            .open(fileName)
            .bufferedReader()
            .use(BufferedReader::readText)
//end readAsset

    fun getWeightedList(context: Context, file: String, table: String): HashMap<String, Int> {
        val tableHashMap = java.util.HashMap<String, Int>()
        var tableArray = JSONArray()
        val thisJSON = JSONObject(FileHelper().readAsset(context, file))

        try {
            tableArray = thisJSON.getJSONArray(table)
          //  Log.d("Weighted List", tableArray.toString())
          //  Log.d("JSON", tableArray.toString())
            for (i in 0 until tableArray.length()) {
                val c = tableArray.getJSONObject(i)
                val name = c.getString("Name")
                val weight = Integer.valueOf(c.getString("Weight"))
                tableHashMap[name] = weight
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tableHashMap
    }

    fun getFromWeightedList(thisList: HashMap<String, Int>): String {
        var listItem: String = ""
        var totalWeight = 0
       // Log.d("LISTY", thisList.toString())
        for (weight in thisList.values) {
            totalWeight += weight
         //   Log.d("Weight", "$weight out of $totalWeight")
        }
        val roll: Int = (1..totalWeight).random()
      //  Log.d("Roll", roll.toString() + "")
        var testWeight = 0
        for ((name, thisWeight) in thisList) {
            testWeight += thisWeight
          //  Log.d("Weights", "Roll: $roll testWeight: $testWeight")
            if (roll <= testWeight) {
                listItem = name
             //   Log.d("Chosen", listItem)
                break
            }
        }
        if (listItem == "") Log.d("WEIGHT LIST","ERROR")
        return listItem
    }

}