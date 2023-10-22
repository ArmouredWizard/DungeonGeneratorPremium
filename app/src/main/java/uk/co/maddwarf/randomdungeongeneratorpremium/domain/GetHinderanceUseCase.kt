package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Hazard
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Obstacle
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trick
import uk.co.maddwarf.randomdungeongeneratorpremium.model.TrickItem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trigger

class GetHindranceUseCase {
    fun getObstacle(context: Context): Obstacle {
        val obstaclesList: List<Obstacle> = getObstaclesList(context = context)
        val chosenObstacle: Obstacle = getObstacleFromList(list = obstaclesList)
        return chosenObstacle
    }

    private fun getObstacleFromList(list: List<Obstacle>): Obstacle {
        var chosenObstacle = Obstacle(name = "Not Defined Yet")
        var totalWeight = 0
        for (obstacle in list) {
            totalWeight += obstacle.weight
        }
        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aObstacle in list) {
            testWeight += aObstacle.weight
            if (roll < testWeight) {
                chosenObstacle = aObstacle
                break
            }
        }
        return chosenObstacle
    }//end getObstacleFromList

    private fun getObstaclesList(context: Context): List<Obstacle> {

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
            val name = c.getString("Name")
            val weight = c.getString("Weight").toInt()
            val description = c.getString("Description")
            thisObstacle = Obstacle(name = name, weight = weight, description = description)
            obstaclesList.add(thisObstacle)
        }
        return obstaclesList
    }//end getObstaclesList

    fun getTrap(context: Context): Trap {
        val trapsList: List<Trap> = getTrapsList(context = context)
        var chosenTrap: Trap = getTrapFromList(list = trapsList)
        chosenTrap = chosenTrap.copy(trigger = getTrigger(context = context))
        return chosenTrap
    }

    private fun getTrapFromList(list: List<Trap>): Trap {
        var chosenTrap = Trap(name = "Not Defined Yet")
        var totalWeight = 0
        for (trap in list) {
            totalWeight += trap.weight
        }
        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aTrap in list) {
            testWeight += aTrap.weight
            if (roll <= testWeight) {
                chosenTrap = aTrap
                break
            }
        }
        return chosenTrap
    }//end getTrapFromList

    private fun getTrapsList(context: Context): List<Trap> {

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
            val name = c.getString("Name")
            val weight = c.getString("Weight").toInt()
            val description = c.getString("Description")
            thisTrap = Trap(name = name, weight = weight, description = description)
            trapList.add(thisTrap)
        }
        return trapList
    }//end getTrapsList

    private fun getTrigger(context: Context): Trigger {
        val triggersList: List<Trigger> = getTriggersList(context = context)
        val chosenTrigger: Trigger = getTriggerFromList(list = triggersList)
        return chosenTrigger
    }

    private fun getTriggerFromList(list: List<Trigger>): Trigger {
        return list.random()
    }//end getTriggerFromList

    private fun getTriggersList(context: Context): List<Trigger> {

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
            val name = c.getString("Name")
            val description = c.getString("Description")
            val weight = c.getString("Weight").toInt()
            thisTrigger = Trigger(name = name, description = description, weight = weight)
            triggerList.add(thisTrigger)
        }
        return triggerList
    }//end getObstaclesList

    fun getTrick(context: Context): Trick {
        val tricksList: List<Trick> = getTricksList(context = context)
        var chosenTrick: Trick = getTrickFromList(list = tricksList)
        chosenTrick = chosenTrick.copy(trickItem = getTrickItem(context = context))
        return chosenTrick
    }

    private fun getTricksList(context: Context): List<Trick> {

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
            val name = c.getString("Name")
            val weight = c.getString("Weight").toInt()
            val description = c.getString("Description")
            thisTrick = Trick(name = name, weight = weight, description = description)
            trickList.add(thisTrick)
        }
        return trickList
    }//end getTricksList

    private fun getTrickFromList(list: List<Trick>): Trick {
        var chosenTrick = Trick(name = "Not Defined Yet")
        var totalWeight = 0
        for (trick in list) {
            totalWeight += trick.weight
        }
        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aTrick in list) {
            testWeight += aTrick.weight
            if (roll < testWeight) {
                chosenTrick = aTrick
                break
            }
        }
        return chosenTrick
    }//end getTrickFromList

    fun getTrickItem(context: Context): TrickItem {
        val trickItemsList: List<TrickItem> = getTrickItemsList(context = context)
        val chosenTrickItem: TrickItem = getTrickItemFromList(list = trickItemsList)
        return chosenTrickItem
    }

    fun getTrickItemFromList(list: List<TrickItem>): TrickItem {
        return list.random()
    }

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
            val name = c.getString("Name")
            val weight = c.getString("Weight").toInt()
            thisTrickItem = TrickItem(name = name, weight = weight)
            trickItemList.add(thisTrickItem)
        }
        return trickItemList
    }//end getTrickItemsList

    fun getHazard(context: Context): Hazard {
        val hazardsList: List<Hazard> = getHazardsList(context = context)
        val chosenHazard: Hazard = getHazardFromList(list = hazardsList)
        return chosenHazard
    }

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
            val name = c.getString("Name")
            val weight = c.getString("Weight").toInt()
            val description = c.getString("Description")
            thisHazard = Hazard(name = name, weight = weight, description = description)
            hazardList.add(thisHazard)
        }
        return hazardList
    }//end getHazardsList

    private fun getHazardFromList(list: List<Hazard>): Hazard {
        var chosenHazard = Hazard(name = "Not Defined Yet")
        var totalWeight = 0
        for (hazard in list) {
            totalWeight += hazard.weight
        }
        val roll: Int = (0..totalWeight).random()
        var testWeight = 0
        for (aHazard in list) {
            testWeight += aHazard.weight
            if (roll < testWeight) {
                chosenHazard = aHazard
                break
            }
        }
        return chosenHazard
    }//end getTrickFromList

}//end Get Hindrance Use Case