package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monster

class GetInhabitantsUseCase() {
    fun getInhabitantsCategories(context: Context, all: Boolean = false): List<String> {
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
        if (all) {
            calledThemes.add(0, "RANDOM")
            calledThemes.add(0, "ALL")
        }
        return calledThemes


    }//end get inhabitants categories

    companion object {
        fun getListOfInhabitants(inhabitants: String, level: Int, context: Context): List<Monster> {
            val inhabitantsList = mutableListOf<Monster>()

            var thisMonster: Monster

            val monsterJsonString: String = FileHelper().readAsset(
                context = context,
                fileName = "inhabitants.json"
            )
            //  Log.d("MONSTER JSON STRING", monsterJsonString)

            val monsterJsonObject = JSONObject(monsterJsonString)

            val monsterArray = monsterJsonObject.getJSONArray(inhabitants)

            for (i in 0 until monsterArray.length()) {
                val c = monsterArray.getJSONObject(i)
                val name = c.getString("Name")
                val weight = c.getString("Weight").toInt()
                val monsterCR = c.getInt("CR")
                val monsterXP = c.getInt("XP")
                thisMonster = Monster(name = name, weight = weight, cr = monsterCR, xp = monsterXP)
                //     Log.d("Monster to add", thisMonster.name + "CR: $monsterCR")
                if (level > 13 && monsterCR > 5 || level <= 13 && monsterCR < level * 2) {
                    inhabitantsList.add(thisMonster)
                    //   Log.d("Monster added", thisMonster.name)
                } else {
                    //   Log.d("Monster Not Added", thisMonster.name)
                }
            }

            return inhabitantsList
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
            Log.d("CANDIDATE MONSTERS", monsterList.toString())
            var returnList = hashMapOf<Monster, Int>()
            var totalMonsterXp = 0
            //for (i in 1..5) {//todo change to While XP < XP Cap,
            while (totalMonsterXp < partyXpLimit) {
                val newMonster = chooseInhabitantFromList(monsterList)
                if (returnList.isEmpty()) {
                    returnList[newMonster] = 1
                    Log.d("Starting Monster", "$newMonster.name, XP: ${newMonster.xp}")
                } else {
                    if ((0..1).random() == 0) {
                        //increment random existing monster
                        val randomIndex: Monster = returnList.keys.random()
                        returnList[randomIndex] = (returnList[randomIndex]!!) + 1
                        Log.d("INCREMENTED RANDOM", randomIndex.name + " XP: ${randomIndex.xp}")
                    } else {
                        //add monster (new or existing)
                        if (returnList.containsKey(newMonster)) {
                            returnList[newMonster] = returnList[newMonster]!! + 1
                            Log.d(
                                "INCREMENTED EXISTING",
                                newMonster.name + "up to " + returnList[newMonster] + " XP: ${newMonster.xp}"
                            )
                        } else {
                            //add new monster
                            returnList[newMonster] = 1
                            Log.d("ADDED", newMonster.name+" XP: ${newMonster.xp}")
                        }
                    }
                }

                var xpMultiplier = 1f
                var xp = 0
                var monsterNumbers = 0

                returnList.forEach() {
                    monsterNumbers += it.value
                    xp+=it.key.xp*it.value
                }
                xpMultiplier = when (monsterNumbers) {
                    1 -> 1f
                    2 -> 1.5f
                    3, 4, 5, 6 -> 2f
                    7, 8, 9, 10 -> 2.5f
                    11, 12, 13, 14 -> 3f
                    else -> 4f
                }

                totalMonsterXp = (xp*xpMultiplier).toInt()
                Log.d("CALCULATION", "$xp * $xpMultiplier = $totalMonsterXp")
                Log.d("XP LIMIT SO FAR", totalMonsterXp.toString()+"out of $partyXpLimit")

            }//end of Loop
            Log.d("ACTUAL LIST OF MONSTERS", returnList.toString())
            return returnList
        }
    }//end build monster content


}//end get inhabitants use case