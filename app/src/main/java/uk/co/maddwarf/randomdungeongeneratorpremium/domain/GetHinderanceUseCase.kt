package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Obstacle
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap
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
            if (roll < testWeight) {
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
    }//end getObstaclesList

    private fun getTrigger(context: Context):Trigger{
        val triggersList: List<Trigger> = getTriggersList(context = context)
        val chosenTrigger: Trigger = getTriggerFromList(list = triggersList)
        return chosenTrigger
    }
    //todo fix above non-implemented functions


}//end Get Hindrance Use Case