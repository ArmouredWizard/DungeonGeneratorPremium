package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monster
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.FileHelper
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.InhabitantsRepository.getInhabitantsCategoriesList
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.InhabitantsRepository.getInhabitantsList

class GetInhabitantsUseCase {
    fun getInhabitantsCategories(context: Context, all: Boolean = false): List<String> {
        val calledThemes = getInhabitantsCategoriesList(context)
        calledThemes.sort()
        if (all) {
            calledThemes.add(0, "RANDOM TYPE")
            calledThemes.add(0, "ALL RANDOM")
        }
        return calledThemes

    }//end get inhabitants categories

    companion object {
        fun getListOfInhabitants(inhabitants: String, level: Int, context: Context): List<Monster> {
            val inhabitantsList =
                getInhabitantsList(inhabitants = inhabitants, context = context, level = level)
            val filteredInhabitantsList = mutableListOf<Monster>()
            inhabitantsList.forEach { thisMonster ->
                if (level > 13 && thisMonster.cr > 5 || level <= 13 && thisMonster.cr < level * 2) {
                    filteredInhabitantsList.add(thisMonster)
                    Log.d("Monster added", thisMonster.name)
                } else {
                    Log.d("Monster Not Added", thisMonster.name)
                }
            }
            return filteredInhabitantsList
        }//end get list of inhabitants

        fun chooseInhabitantFromList(fullMonsterList: List<Monster>): Monster {

            var totalWeight = 0
            for (aMonster in fullMonsterList) {
                totalWeight += aMonster.weight
            }

            val roll: Int = (0..totalWeight).random()
            var testWeight = 0
            for (aMonster in fullMonsterList) {
                val thisWeight: Int = aMonster.weight
                testWeight += thisWeight
                if (roll <= testWeight) {
                    return aMonster
                }
            }
            return Monster(name = "Blank Monster")
        }//end choose Inhabitant from List

        fun buildMonstersContent(
            context: Context,
            inhabitants: String,
            level: Int,
            pcNumbers: Int
        ): HashMap<Monster, Int> {

            val pcXpLimit = intArrayOf(
                0, 75, 150, 225, 375, 750, 900, 1100, 1400, 1600, 1900, 2400, 3000,
                3400, 3800, 4300, 4800, 5900, 6300, 7300, 8500
            )
            val partyXpLimit = pcXpLimit[level] * pcNumbers

            val monsterList =
                getListOfInhabitants(
                    context = context,
                    inhabitants = inhabitants,
                    level = level
                )
            val returnList = hashMapOf<Monster, Int>()
            var totalMonsterXp = 0
            while (totalMonsterXp < partyXpLimit) {
                val newMonster = chooseInhabitantFromList(monsterList)
                if (returnList.isEmpty()) {
                    returnList[newMonster] = 1
                } else {
                    if ((0..1).random() == 0) {
                        //increment random existing monster
                        val randomIndex: Monster = returnList.keys.random()
                        returnList[randomIndex] = (returnList[randomIndex]!!) + 1
                    } else {
                        //add monster (new or existing)
                        if (returnList.containsKey(newMonster)) {
                            returnList[newMonster] = returnList[newMonster]!! + 1
                        } else {
                            //add new monster
                            returnList[newMonster] = 1
                        }
                    }
                }

                var xp = 0
                var monsterNumbers = 0

                returnList.forEach() {
                    monsterNumbers += it.value
                    xp += it.key.xp * it.value
                }
                val xpMultiplier = when (monsterNumbers) {
                    1 -> 1f
                    2 -> 1.5f
                    3, 4, 5, 6 -> 2f
                    7, 8, 9, 10 -> 2.5f
                    11, 12, 13, 14 -> 3f
                    else -> 4f
                }

                totalMonsterXp = (xp * xpMultiplier).toInt()
            }//end of Loop
            return returnList
        }
    }//end build monster content

}//end get inhabitants use case