package uk.co.maddwarf.randomdungeongeneratorpremium.model

open class MundaneItem(
    open val name: String
)

data class Gem(
    val name: String,
    val description: String = "",
    val value: Int = 0
)

data class Art(
    val name: String,
    val description: String = "",
    val value: Int = 0
)