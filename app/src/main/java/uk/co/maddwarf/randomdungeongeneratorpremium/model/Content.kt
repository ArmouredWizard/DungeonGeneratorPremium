package uk.co.maddwarf.randomdungeongeneratorpremium.model

sealed class Content

data class Monsters(
    val monsters: HashMap<Monster, Int>
) : Content()

fun Monsters.totalXp(): Int {
    var totalXp = 0
    monsters.forEach {
        totalXp += it.value * it.key.xp
    }
    return totalXp
}

sealed class Hindrance() : Content()

data class Obstacle(
    val name: String,
    val description: String = "",
    val weight: Int = 1
) : Hindrance()

data class Trap(
    val name: String,
    val description: String = "",
    val trigger: Trigger = Trigger(name = "No Trigger"),
    val weight: Int = 1
) : Hindrance()

data class Trigger(
    val name: String,
    val description: String = "",
    val weight: Int = 1
)

data class Trick(
    val name: String,
    val description: String = "",
    val weight: Int = 1,
    val trickItem: TrickItem = TrickItem(name = "No Item")
) : Hindrance()

data class TrickItem(
    val name: String,
    val weight: Int = 1
)

data class Hazard(
    val name: String,
    val description: String = "",
    val weight: Int = 1
) : Hindrance()

data class Empty(
    val name: String,
    val description: String = ""
) : Content()

data class Loot(
    val name: String,
    val coins: Cash = Cash(),
    val gemList: List<Gem> = listOf(),
    val artList: List<Art> = listOf(),
    val magicItemsList: List<Magic> = listOf()
) : Content()