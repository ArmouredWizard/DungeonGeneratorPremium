package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Hazard
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Obstacle
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trick
import uk.co.maddwarf.randomdungeongeneratorpremium.model.TrickItem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trigger
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository.getHazardsList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository.getObstaclesList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository.getTrapsList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository.getTrickItemsList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository.getTricksList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.HindrancesRepository.getTriggersList

class GetHindranceUseCase {
    fun getObstacle(context: Context): Obstacle {
        val obstaclesList: List<Obstacle> = getObstaclesList(context = context)
        val chosenObstacle: Obstacle = chooseObstacleFromList(list = obstaclesList)
        return chosenObstacle
    }

    private fun chooseObstacleFromList(list: List<Obstacle>): Obstacle {
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

    fun getTrap(context: Context): Trap {
        val trapsList: List<Trap> = getTrapsList(context = context)
        var chosenTrap: Trap = chooseTrapFromList(list = trapsList)
        chosenTrap = chosenTrap.copy(trigger = getTrigger(context = context))
        return chosenTrap
    }

    private fun chooseTrapFromList(list: List<Trap>): Trap {
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

    private fun getTrigger(context: Context): Trigger {
        val triggersList: List<Trigger> = getTriggersList(context = context)
        val chosenTrigger: Trigger = chooseTriggerFromList(list = triggersList)
        return chosenTrigger
    }

    private fun chooseTriggerFromList(list: List<Trigger>): Trigger {
        return list.random()
    }//end getTriggerFromList

    fun getTrick(context: Context): Trick {
        val tricksList: List<Trick> = getTricksList(context = context)
        var chosenTrick: Trick = chooseTrickFromList(list = tricksList)
        chosenTrick = chosenTrick.copy(trickItem = getTrickItem(context = context))
        return chosenTrick
    }

    private fun chooseTrickFromList(list: List<Trick>): Trick {
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

    private fun getTrickItem(context: Context): TrickItem {
        val trickItemsList: List<TrickItem> = getTrickItemsList(context = context)
        val chosenTrickItem: TrickItem = chooseTrickItemFromList(list = trickItemsList)
        return chosenTrickItem
    }

    private fun chooseTrickItemFromList(list: List<TrickItem>): TrickItem {
        return list.random()
    }

    fun getHazard(context: Context): Hazard {
        val hazardsList: List<Hazard> = getHazardsList(context = context)
        val chosenHazard: Hazard = chooseHazardFromList(list = hazardsList)
        return chosenHazard
    }

    private fun chooseHazardFromList(list: List<Hazard>): Hazard {
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