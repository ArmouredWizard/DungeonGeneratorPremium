package uk.co.maddwarf.randomdungeongeneratorpremium.repository

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

            for (i in 0 until tableArray.length()) {
                val c = tableArray.getJSONObject(i)
                val name = c.getString("name")
                val weight = Integer.valueOf(c.getString("weight"))
                tableHashMap[name] = weight
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return tableHashMap
    }

    fun chooseFromWeightedList(thisList: HashMap<String, Int>): String {//todo should not be in repo??
        var listItem: String = ""
        var totalWeight = 0
        for (weight in thisList.values) {
            totalWeight += weight
        }
        val roll: Int = (1..totalWeight).random()
        var testWeight = 0
        for ((name, thisWeight) in thisList) {
            testWeight += thisWeight
            if (roll <= testWeight) {
                listItem = name
                break
            }
        }
        if (listItem == "") Log.d("WEIGHT LIST", "ERROR")
        return listItem
    }
}