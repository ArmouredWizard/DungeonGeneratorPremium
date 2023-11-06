package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context
import android.util.Log
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Content
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Empty
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monsters
import uk.co.maddwarf.randomdungeongeneratorpremium.repository.FileHelper
import javax.inject.Inject

class PopulateRoomUseCase @Inject constructor(val inhabitantsUseCase: GetInhabitantsUseCase) {

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
                inhabitantsUseCase.getInhabitantsCategories(context = context, all = false)
                    .random()
        }

        val contentsType = fileHelper.chooseFromWeightedList(contentsList)
        Log.d("ROOM CONTENTS", contentsType)
        val contents: List<Content> = when (contentsType) {
            "Dominant Inhabitant" -> {
                listOf(
                    Monsters(
                        inhabitantsUseCase.buildMonstersContent(
                            context = context,
                            inhabitants = theInhabitants,
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    ),
                    GetLootUseCase().buildLoot(
                        type = "Individual",
                        level = level,
                        context = context
                    )
                )
            }

            "Dominant Inhabitant with Treasure" -> {
                listOf(
                    Monsters(
                        inhabitantsUseCase.buildMonstersContent(
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
                listOf(
                    Monsters(
                        inhabitantsUseCase.buildMonstersContent(
                            context = context,
                            inhabitants = "Wanderers",
                            level = level,
                            pcNumbers = pcNumbers
                        )
                    )
                )
            }

            "Random Monster with Treasure" -> {
                listOf(
                    Monsters(
                        inhabitantsUseCase.buildMonstersContent(
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
                listOf(
                    GetHindranceUseCase().getObstacle(context = context)
                )
            }

            "Trap" -> {
                listOf(
                    GetHindranceUseCase().getTrap(context = context)
                )
            }

            "Trap with Treasure" -> {
                listOf(
                    GetHindranceUseCase().getTrap(context = context),
                    GetLootUseCase().buildLoot(type = "Hoard", level = level, context = context)
                )
            }

            "Trick" -> {
                listOf(
                    GetHindranceUseCase().getTrick(context = context)
                )
            }

            "Hazard" -> {
                listOf(
                    GetHindranceUseCase().getHazard(context = context)
                )
            }

            "Empty" -> {
                listOf(Empty(name = "There is nothing of note here"))
            }

            "Empty with Treasure" -> {
                listOf(
                    Empty(name = "This treasure is unguarded"),
                    GetLootUseCase().buildLoot(type = "Hoard", level = level, context = context)
                )
            }

            else -> {
                listOf()
            }
        }
        Log.d("CONTENTS", contents.toString())

        return contents
    }

}//end PopulateRoom Use Case

