package uk.co.maddwarf.randomdungeongeneratorpremium.repository

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Hazard
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Obstacle
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trick
import uk.co.maddwarf.randomdungeongeneratorpremium.model.TrickItem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trigger

object HindrancesRepository {

    fun getObstaclesList(context: Context): List<Obstacle> {

        val obstacleJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "obstacles.json"
        )

        val obstacleJsonObject = JSONObject(obstacleJsonString)
        val obstacleArray = obstacleJsonObject.getJSONArray("Obstacles")

        val obstaclesList = mutableListOf<Obstacle>()
        var thisObstacle: Obstacle

        for (i in 0 until obstacleArray.length()) {
            val c = obstacleArray.getJSONObject(i)
            val typeToken = object : TypeToken<Obstacle>() {}.type
            thisObstacle = Gson().fromJson(c.toString(), typeToken)
            obstaclesList.add(thisObstacle)
        }
        Log.d("OBSTACLE LIST", obstaclesList.toString())
        return obstaclesList
    }//end getObstaclesList

    fun getTrapsList(context: Context): List<Trap> {

        val trapJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "traps.json"
        )

        val trapJsonObject = JSONObject(trapJsonString)
        val trapArray = trapJsonObject.getJSONArray("Traps")

        val trapList = mutableListOf<Trap>()
        var thisTrap: Trap

        for (i in 0 until trapArray.length()) {
            val c = trapArray.getJSONObject(i)
            val typeToken = object : TypeToken<Trap>() {}.type
            thisTrap = Gson().fromJson(c.toString(), typeToken)
            trapList.add(thisTrap)
        }
        return trapList
    }//end getTrapsList

    fun getTriggersList(context: Context): List<Trigger> {

        val triggerJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "triggers.json"
        )

        val triggerJsonObject = JSONObject(triggerJsonString)
        val triggerArray = triggerJsonObject.getJSONArray("Triggers")

        val triggerList = mutableListOf<Trigger>()
        var thisTrigger: Trigger

        for (i in 0 until triggerArray.length()) {
            val c = triggerArray.getJSONObject(i)
            val typeToken = object : TypeToken<Trigger>() {}.type
            thisTrigger = Gson().fromJson(c.toString(), typeToken)
            triggerList.add(thisTrigger)
        }
        return triggerList
    }//end getTriggersList

    fun getTricksList(context: Context): List<Trick> {

        val trickJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "tricks.json"
        )

        val trickJsonObject = JSONObject(trickJsonString)
        val trickArray = trickJsonObject.getJSONArray("Tricks")

        val trickList = mutableListOf<Trick>()
        var thisTrick: Trick

        for (i in 0 until trickArray.length()) {
            val c = trickArray.getJSONObject(i)
            val typeToken = object : TypeToken<Trick>() {}.type
            thisTrick = Gson().fromJson(c.toString(), typeToken)
            trickList.add(thisTrick)
        }
        return trickList
    }//end getTricksList

    fun getTrickItemsList(context: Context): List<TrickItem> {
        val trickItemJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "trickitems.json"
        )

        val trickItemJsonObject = JSONObject(trickItemJsonString)
        val trickItemArray = trickItemJsonObject.getJSONArray("Trick Items")

        val trickItemList = mutableListOf<TrickItem>()
        var thisTrickItem: TrickItem

        for (i in 0 until trickItemArray.length()) {
            val c = trickItemArray.getJSONObject(i)
            val typeToken = object : TypeToken<TrickItem>() {}.type
            thisTrickItem = Gson().fromJson(c.toString(), typeToken)
            trickItemList.add(thisTrickItem)
        }
        return trickItemList
    }//end getTrickItemsList

    fun getHazardsList(context: Context): List<Hazard> {
        val hazardJsonString: String = FileHelper().readAsset(
            context = context,
            fileName = "hazards.json"
        )

        val hazardJsonObject = JSONObject(hazardJsonString)
        val hazardArray = hazardJsonObject.getJSONArray("Hazards")

        val hazardList = mutableListOf<Hazard>()
        var thisHazard: Hazard

        for (i in 0 until hazardArray.length()) {
            val c = hazardArray.getJSONObject(i)
            val typeToken = object : TypeToken<Hazard>() {}.type
            thisHazard = Gson().fromJson(c.toString(), typeToken)
            hazardList.add(thisHazard)
        }
        return hazardList
    }//end getHazardsList


}