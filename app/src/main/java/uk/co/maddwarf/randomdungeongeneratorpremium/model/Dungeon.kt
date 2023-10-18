package uk.co.maddwarf.randomdungeongeneratorpremium.model

data class Dungeon(
    val map: Array<Array<String>>,
    val height: Int = 0,
    val width: Int = 0,
    var roomList: List<Room>,
    val corridorsList: List<Corridor>
)