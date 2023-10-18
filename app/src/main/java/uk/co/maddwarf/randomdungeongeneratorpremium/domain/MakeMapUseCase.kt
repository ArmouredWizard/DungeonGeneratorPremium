package uk.co.maddwarf.randomdungeongeneratorpremium.domain

import android.graphics.Rect
import android.graphics.Rect.intersects
import android.util.Log
import androidx.compose.ui.geometry.Offset
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Corridor
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Dungeon
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Room
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.OddifyUseCase.oddify
import uk.co.maddwarf.randomdungeongeneratorpremium.model.getOddCenter

class MakeMapUseCase {

    fun makeMap(dungeonWidth: Int, dungeonHeight: Int): Dungeon {

Log.d("MAKING DUNGEON", "LOADED")

//create Dungeon
        //val numberOfRooms = (dungeonWidth / 7) + 1 //rewrite as WHEN with "sparsity" choice
        val numberOfRooms = 100
        var roomsList = mutableListOf<Room>()

        //todo replace with Class
        /*
        Dungeon Codes:
            V# = Void (dirt)
            R# = Room
            C# = Corridor
            S# = Solid rock
            D# = Door
            u# = stairs up
            d# = stairs down
*/

        //initialise martix
        var mapArray = initializeMatrix(dungeonWidth, dungeonHeight)

        //create Rooms
        createRooms(
            numberOfRooms,
            dungeonHeight,
            dungeonWidth,
            roomsList
        )
        for (room in roomsList) {
            addRoomToMatrix(room.rectangle, mapArray)
        }

        val corridorList = mutableListOf<Corridor>()

        //create entrance corridor
        corridorList += makeEntranceCorridor(roomsList, dungeonHeight)

        //create Corridors
        val (corridorsList, roomList) = makeCorridors(roomsList)
        roomsList = roomList

        corridorList += corridorsList

        for (corridor in corridorList) {
            mapArray = addCorridorToMatrix(mapArray = mapArray, corridor = corridor)
        }

        return Dungeon(
            map = mapArray,
            roomList = roomsList,
            corridorsList = corridorList,
            height = dungeonHeight,
            width = dungeonWidth
        )
    }//end makeMap

    private fun makeEntranceCorridor(roomsList: MutableList<Room>, dungeonHeight: Int): Corridor {
        //todo check for connects
        return Corridor(
            start = Offset(x = roomsList[0].getOddCenter().x, y = roomsList[0].getOddCenter().y),
            end = Offset(
                x = roomsList[0].getOddCenter().x,
                y = dungeonHeight - 2.toFloat()
            )
        )
    }//end make entrance corridor

    private fun createRooms(
        numberOfRooms: Int,
        dungeonHeight: Int,
        dungeonWidth: Int,
        roomsList: MutableList<Room>,
    ) {
        var index = 1
        var clashes = 0

        while (roomsList.size < numberOfRooms) {
            val roomRect = makeRect(dungeonHeight, dungeonWidth)
            if (intersect(roomRect, roomsList, dungeonWidth, dungeonHeight)) {
                clashes++
             //   Log.d("CLASHES", "Room ${roomsList.size}, Clash:$clashes")
                if (clashes > 50) return
            } else {
                roomsList.add(
                    Room(
                        index = index,
                        rectangle = roomRect
                    )
                )
                index++
                clashes = 0
            }
        }//end WHILE rooms < number of rooms
    }//end Create Rooms

    private fun initializeMatrix(
        dungeonWidth: Int,
        dungeonHeight: Int,
    ): Array<Array<String>> {

        val mapArray: Array<Array<String>> = Array(dungeonHeight) {
            Array(dungeonWidth) {
                String()
            }
        }

        for (dWidth in 0 until dungeonWidth) {
            for (dHeight in 0 until dungeonHeight) {
                mapArray[dHeight][dWidth] = "S0"
                if (dWidth == 0 || dHeight == 0 || dWidth == dungeonWidth - 1 || dHeight == dungeonHeight - 1) {
                    mapArray[dHeight][dWidth] = "V0"
                }
            }//end inner init loop
        }//emd outer loop
        return mapArray
    }//end Initialize Matrix

    private fun makeRect(dungeonHeight: Int, dungeonWidth: Int): Rect {

        val x1 = oddify((1..(dungeonWidth - 8)).random() + 2)
        val y1 = oddify((1..(dungeonHeight - 10)).random() + 2)
        val x2 = oddify(x1 + (1..dungeonWidth / 9).random() + 4)
        val y2 = oddify(y1 + (1..dungeonHeight / 9).random() + 4)

        return Rect(x1, y1, x2, y2)
    }//end Make Rectangle

    private fun intersect(
        newRoomRectangle: Rect,
        roomsList: List<Room>,
        dungeonWidth: Int,
        dungeonHeight: Int
    ): Boolean {

        if (newRoomRectangle.left < 1 || newRoomRectangle.top < 1) {
            return true
        }
        if (newRoomRectangle.bottom > (dungeonHeight - 3) || newRoomRectangle.right > dungeonWidth - 3) {
            return true
        }

        val expandedRectangle = Rect(
            newRoomRectangle.left - 1,
            newRoomRectangle.top - 1,
            newRoomRectangle.right + 1,
            newRoomRectangle.bottom + 1
        )

        for (room in roomsList) {
            if (expandedRectangle.intersect(room.rectangle)) {
                return true
            }
        }

        return false
    }//end Intersect

    private fun addRoomToMatrix(
        roomRect: Rect,
        mapArray: Array<Array<String>>
    ): Array<Array<String>> {

        for (x in (roomRect.left..roomRect.right)) {
            for (y in (roomRect.top..roomRect.bottom)) {
                mapArray[y][x] = "R"
            }
        }
        return mapArray
    }//end Add Room to Matrix

    private fun makeCorridors(roomsList: MutableList<Room>): Pair<List<Corridor>, MutableList<Room>> {
        val corridorsList = mutableListOf<Corridor>()

        for (roomIndex in roomsList.indices) {
            if (roomIndex == roomsList.size - 1) {
                break
            }
           if (roomsList[roomIndex + 1].connected) continue
            val corridor: Corridor = Corridor(
                start = roomsList[roomIndex].getOddCenter(),
                end = roomsList[roomIndex + 1].getOddCenter()
            )
            corridorsList.add(corridor)
            val corridorHRect = Rect(
                corridor.start.x.toInt(),
                corridor.start.y.toInt(),
                corridor.end.x.toInt(),
                (corridor.start.y + 1).toInt()
            )
            val corridorVRect = Rect(
                corridor.end.x.toInt(),
                corridor.start.y.toInt(),
                (corridor.end.x + 1).toInt(),
                corridor.end.y.toInt()
            )

            for (room in roomsList) {
                if ((intersects(corridorVRect, room.rectangle))
                    || (intersects(corridorHRect, room.rectangle))
                ) {
                    room.connected = true
                }
            }
        }//end IF NOT CONNECTED
//end rooms loop

        return Pair(corridorsList, roomsList)
    }//end Make Corridors

    private fun addCorridorToMatrix(
        mapArray: Array<Array<String>>,
        corridor: Corridor
    ): Array<Array<String>> {

        //make horizontal section
        var start = corridor.start.x.toInt()
        var end = corridor.end.x.toInt()
        val y = corridor.start.y.toInt()

        if (start > end) {
            start = end.also { end = start }
        }

        for (x in (start..end)) {
            if (mapArray[y][x] == "S0") {
                mapArray[y][x] = "C"
            }
            if ((mapArray[y][x - 1] == "R" && mapArray[y][x] != "R")
                || (mapArray[y][x + 1] == "R" && mapArray[y][x] != "R")
            ) {
                mapArray[y][x] = "VD"
            }
        }
        //make vertical section
        start = corridor.start.y.toInt()
        end = corridor.end.y.toInt()
        val x = corridor.end.x.toInt()

        if (start > end) {
            start = end.also { end = start }
        }

        for (y in (start..end)) {
            if (mapArray[y][x] == "S0") {
                mapArray[y][x] = "C"
            }
            if ((mapArray[y - 1][x] == "R" && mapArray[y][x] != "R")
                || (mapArray[y + 1][x] == "R" && mapArray[y][x] != "R")
            ) {
                mapArray[y][x] = "HD"
            }
        }

        return mapArray
    }//end Add Corridor to Matrix

}//end MakeMapUseCase
