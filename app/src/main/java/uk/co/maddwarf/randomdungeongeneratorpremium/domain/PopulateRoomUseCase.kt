package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.content.Context

class PopulateRoomUseCase {

    fun populateRoom(context: Context, inhabitants: String): String {

        val fileHelper = FileHelper()

        val contentsList = fileHelper.getWeightedList(
            context = context,
            file = "contents.json",
            table = "Contents"
        )

        val contentsType = fileHelper.getFromWeightedList(contentsList)

        val contents: String = when (contentsType) {
            "Dominant Inhabitant" -> {
                val inhabitantsList = fileHelper.getWeightedList(context, "inhabitants.json", inhabitants)
                return fileHelper.getFromWeightedList(inhabitantsList)
            }

            "Dominant Inhabitant with Treasure" -> {
                val inhabitantsList = fileHelper.getWeightedList(context, "inhabitants.json", inhabitants)
                return fileHelper.getFromWeightedList(inhabitantsList) + " with Loot"
            }

            "Random Monster" -> {
                "RANDOM"
            }

            "Random Monster with Treasure" -> {
                "RANDOM WITH LOOT"
            }

            "Obstacle" -> {
                "Obstacles"
            }

            "Trap" -> {
                "Traps"
            }

            "Trap with Treasure" -> {
                "Traps and Loots"
            }

            "Trick" -> {
                "Tricks"
            }

            "Hazard" -> {
                "Hazards"
            }

            "Empty" -> {
                "Nothing in this room\nJust a long test for text\nthat might overglowe\njust how uch room do we have\n and does it scroll?\n #DoesItScroll\nDoes it Heck\nmore\nmore\nLauren Ipsen"
            }

            "Empty with Treasure" -> {
                "Just loot"
            }

            else -> {
                "Unknown encounter type"
            }
        }

        val newString = "There are $contentsType in this room. \n$contents"

        return newString
    }


}//end PopulateRoom Use Case

