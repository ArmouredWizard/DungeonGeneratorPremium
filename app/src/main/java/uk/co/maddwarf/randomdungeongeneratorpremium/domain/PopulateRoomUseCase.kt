package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.GetInhabitantsUseCase.Companion.buildMonstersContent
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Content
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Empty
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monsters
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap

class PopulateRoomUseCase {

    fun populateRoom(
        context: Context,
        inhabitants: String,
        level: Int,
        pcNumbers: Int
    ): List<Content> {

        val fileHelper = FileHelper()

        val contentsList = fileHelper.getWeightedList(
            context = context,
            file = "contents.json",
            table = "Contents"
        )

        var theInhabitants: String = inhabitants
        if (inhabitants == "ALL RANDOM") {
            theInhabitants =
                GetInhabitantsUseCase().getInhabitantsCategories(context = context, all = false)
                    .random()
        }

        val contentsType = fileHelper.getFromWeightedList(contentsList)

        val contents: List<Content> = when (contentsType) {
            "Dominant Inhabitant" -> {
                listOf(
                    Monsters(
                        buildMonstersContent(
                            context = context,
                            inhabitants = theInhabitants,
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    ),
                    GetLootUseCase().buildLoot(type = "Individual", level = level, context = context)
                )
            }

            "Dominant Inhabitant with Treasure" -> {
                listOf(
                    Monsters(
                        buildMonstersContent(
                            context = context,
                            inhabitants = theInhabitants,
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    ),
                    GetLootUseCase().buildLoot(type = "Hoard", level = level, context = context)
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
                    GetLootUseCase().buildLoot(type = "Hoard", level = level, context = context)
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
                    GetLootUseCase().buildLoot(type = "Hoard", level = level, context = context)
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
                    GetLootUseCase().buildLoot(type = "Hoard", level = level, context = context)
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

