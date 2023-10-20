package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.GetInhabitantsUseCase.Companion.buildMonstersContent
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Content
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monsters
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap

class PopulateRoomUseCase {

    fun populateRoom(context: Context, inhabitants: String, level: Int): List<Content> {

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
                            level = level
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
                            level = level
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
                            level = level
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
                            level = level
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
                )            }

            "Hazard" -> {
                //   "Hazards"
                listOf(Trap(name = "Hazard"))
            }

            "Empty" -> {
                // "Nothing in this room\nJust a long test for text\nthat might overglowe\njust how uch room do we have\n and does it scroll?\n #DoesItScroll\nDoes it Heck\nmore\nmore\nLauren Ipsen"
                listOf(Trap(name = "EMPTY"))
            }

            "Empty with Treasure" -> {
                //  "Just loot"
                listOf(
                    Trap(name = "Just LOOTS"),//EMPTY
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

