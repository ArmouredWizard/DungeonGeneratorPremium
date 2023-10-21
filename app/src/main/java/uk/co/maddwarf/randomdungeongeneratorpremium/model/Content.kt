package uk.co.maddwarf.randomdungeongeneratorpremium.model

sealed class Content()

data class Monsters(
    val monsters: HashMap<Monster, Int>
) : Content()

data class Obstacle(
    val name: String,
    val description: String = "",
    val weight: Int = 1
) : Content()

data class Trap(
    val name: String,
    val description: String = "",
    val trigger: Trigger = Trigger(name = "No Trigger"),
    val weight: Int = 1
) : Content()

data class Trigger(
    val name: String,
    val description: String = "",
    val weight:Int = 1
)

data class Trick(
    val name: String,
    val description: String = "",
    val weight: Int = 1,
    val trickItem: TrickItem = TrickItem(name = "No Item")
):Content()

data class TrickItem(
    val name:String,
    val weight:Int = 1
):Content()
