package uk.co.maddwarf.randomdungeongeneratorpremium.domain

object OddifyUseCase {
    fun oddify(toOddify: Int): Int {
        var returnInt = toOddify
        if (returnInt % 2 == 0) {
            val RANDY: Int = (1..2).random()
            if (RANDY == 1) {
                returnInt += 1
            } else {
                returnInt -= 1
            }
        }
        return returnInt
    }//end oddifier

    fun evenify(toEvenify:Int):Int{
        var returnInt = toEvenify
        if (returnInt % 2 != 0) {
            val RANDY: Int = (1..2).random()
            if (RANDY == 1) {
                returnInt += 1
            } else {
                returnInt -= 1
            }
        }
        return returnInt
    }
}