package uk.co.maddwarf.randomdungeongeneratorpremium.model

data class Monster(
    val name: String,
    val xp: Int = 0,
    val cr: Float = 0f,
    val weight: Int = 1

)