package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.util.Log

class Dice {

    fun roll(diceToRoll: String): Int {

        val diceArray = diceToRoll.split("d")
        val numberOfDice = diceArray[0].toInt()
        val typeOfDice = diceArray[1].toInt()

        if (numberOfDice == 0 || typeOfDice == 0) return 0

        var result = 0
        for (roll in (1..numberOfDice)) {
            result += (1..typeOfDice).random()
        }
        return result
    }

}