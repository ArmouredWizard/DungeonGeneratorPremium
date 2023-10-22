package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.GetInhabitantsUseCase.Companion.buildMonstersContent
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Content
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Empty
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monsters
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap

class PopulateRoomUseCase {

    fun populateRoom(context: Context, inhabitants: String, level: Int, pcNumbers: Int): List<Content> {

        val fileHelper = FileHelper()

        val contentsList = fileHelper.getWeightedList(
            context = context,
            file = "contents.json",
            table = "Contents"
        )

        var theInhabitants: String = inhabitants
        if (inhabitants == "ALL") {
            Log.d("ALL", inhabitants)
            theInhabitants =
                GetInhabitantsUseCase().getInhabitantsCategories(context = context, all = false)
                    .random()
            Log.d("RANDOM", "Chosen: $theInhabitants")
        }

        val contentsType = fileHelper.getFromWeightedList(contentsList)

        val contents: List<Content> = when (contentsType) {
            "Dominant Inhabitant" -> {
                listOf(
                    Monsters(
                        buildMonstersContent(
                            context = context,
                            inhabitants = inhabitants,
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    ),
                    //todo add personal loot
                    Trap(name = "THIS IS A SMALL LOOT")
                )
            }

            "Dominant Inhabitant with Treasure" -> {
                listOf(
                    Monsters(
                        buildMonstersContent(
                            context = context,
                            inhabitants = inhabitants,
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    ),
                    //todo insert hoard Loot here
                    Trap(name = "HOARD OF LOOT")
                )
            }

            "Random Monster" -> {
                //"RANDOM"
                listOf(
                    Monsters(
                        buildMonstersContent(
                            context = context,
                            inhabitants = "Wanderers",
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    )
                )
            }

            "Random Monster with Treasure" -> {
                //"RANDOM WITH LOOT"
                listOf(
                    Monsters(
                        buildMonstersContent(
                            context = context,
                            inhabitants = "Wanderers",
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    ),
                    //todo add hoard loot
                )
            }

            "Obstacle" -> {
                // "Obstacles"
                listOf(
                    GetHindranceUseCase().getObstacle(context = context)
                )
            }

            "Trap" -> {
                //"Traps"
                listOf(
                    GetHindranceUseCase().getTrap(context = context)
                )
            }

            "Trap with Treasure" -> {
                // "Traps and Loots"
                listOf(
                    GetHindranceUseCase().getTrap(context = context),
                    //insert loot here
                    Trap(name = "LOOTS") //todo ADD LOOTS
                )
            }

            "Trick" -> {
                //  "Tricks"
                listOf(
                    GetHindranceUseCase().getTrick(context = context)
                )
            }

            "Hazard" -> {
                //   "Hazards"
                listOf(
                    GetHindranceUseCase().getHazard(context = context)
                )
            }

            "Empty" -> {
                listOf(Empty(name = "There is nothing of note here"))
            }

            "Empty with Treasure" -> {
                //  "Just loot"
                listOf(
                    Empty(name = "This treasure is unguarded"),
                    Trap(name = "Just LOOTS"),
                    //todo add loot
                )
            }

            else -> {
                listOf(Trap(name = "Unknown encounter type: $contentsType"))
            }
        }
        Log.d("CONTENTS", contents.toString())

        return contents
    }


}//end PopulateRoom Use Case

