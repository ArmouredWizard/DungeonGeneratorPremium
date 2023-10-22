package uk.co.maddwarf.randomdungeongeneratorpremium.ui.screens

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.Rect.intersects
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.maddwarf.randomdungeongeneratorpremium.R
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.GetInhabitantsUseCase
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.MakeMapUseCase
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.PopulateRoomUseCase
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Dungeon
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Room

class MapViewModel(application: Application, savedStateHandle: SavedStateHandle) :
    AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext

    private var _uiState = MutableStateFlow(
        MapUiState(
            dungeon = Dungeon(
                map = arrayOf(),
                roomList = listOf<Room>(),
                corridorsList = listOf(),
            ),
            clickTile = Offset(0f, 0f)
        )
    )

    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    fun updateUiState(dungeon: Dungeon) {
        _uiState.value = MapUiState(
            dungeon = dungeon,
            clickTile = Offset(0f, 0f)
        )
    }

    var incomingData: String =
        checkNotNull(savedStateHandle[MapScreenDestination.itemIdArg])
    var mapSize = incomingData.split(",")[0].trim()
    var inhabitants = incomingData.split(",")[1].trim()
    var level = incomingData.split(",")[2].trim()
    var layers = incomingData.split(",")[3].trim()
    var pcNumbers = incomingData.split(",")[4].trim()

    var tileSizeInPx = 0

    var screenWidthInPx = 0f
    var screenHeightInPx = 0f

    var clickedRoom = Room(index = 0, rectangle = Rect())

    fun updateScreenSizePx(width: Float, height: Float) {
        screenHeightInPx = height
        screenWidthInPx = width
    }

    fun makeMap() {
        if (screenWidthInPx < 100) {
            return
        }
        val dungeonWidth = when (mapSize) {
            "Small" -> 55
            "Medium" -> 73
            "Large" -> 91
            "Huge" -> 121
            else -> (40..120).random()
        }

        tileSizeInPx = ((screenWidthInPx - 100) / dungeonWidth).toInt()

        var dungeonHeight = (screenHeightInPx / tileSizeInPx).toInt()

        if (dungeonHeight % 2 == 0) {
            dungeonHeight--
        }
        Log.d("SCREEN SIZE", "h: $screenHeightInPx, w: $screenWidthInPx")
        Log.d("TILE SIZE", "$tileSizeInPx")
        Log.d("DUNGEON SIZE", "H: $dungeonHeight, w: $dungeonWidth")

        var newDungeon = MakeMapUseCase().makeMap(
            dungeonHeight = dungeonHeight,
            dungeonWidth = dungeonWidth
        )

        populateRooms(roomList = newDungeon.roomList, level = level.toInt(), pcNumbers = pcNumbers.toInt())

        updateUiState(
            dungeon = newDungeon
        )
    }//end make map

    fun populateRooms(roomList: List<Room>, level: Int, pcNumbers: Int) {
        var theInhabitants = inhabitants
        if (inhabitants == "RANDOM"){
            Log.d("RANDOM", inhabitants)
            theInhabitants = GetInhabitantsUseCase().getInhabitantsCategories(context = context, all = false).random()
            Log.d("RANDOM", "Chosen: $theInhabitants")
        }
        for (room in roomList) {
            room.contents =
                PopulateRoomUseCase().populateRoom(context = context, inhabitants = theInhabitants, level = level, pcNumbers = pcNumbers)
        }
    }

    fun refreshMap(size: String) {
        mapSize = size
        makeMap()
    }

    fun doCanvasClick(clicked: Offset) {
        clickedRoom = Room(index = 0, rectangle = Rect())
        uiState.value.clickTile = clicked / tileSizeInPx.toFloat()
        val clickTile = uiState.value.clickTile
        val clickedRect = Rect(
            /* left = */ clickTile.x.toInt(),
            /* top = */clickTile.y.toInt(),
            /* right = */(clickTile.x + 1).toInt(),
            /* bottom = */(clickTile.y + 1).toInt()
        )
        Log.d("SMALL OFFSET", clickTile.toString())
        for (room in uiState.value.dungeon.roomList) {
            val testRect = Rect(
                /* left = */ room.rectangle.left,
                /* top = */room.rectangle.top,
                /* right = */room.rectangle.right + 1,
                /* bottom = */room.rectangle.bottom + 1
            )
            Log.d("INDEX", "${room.index}: ${room.rectangle}")

            if (intersects(testRect, clickedRect)) {
                Log.d("ROOM CLICKED", "${room.index}")
                clickedRoom = room
            }
        }
    }//end do canvas click

    @Composable
    fun initTileMap(): HashMap<String, ImageBitmap> {
        val n = 0
        val newSize = tileSizeInPx + n
        return hashMapOf(
            Pair("VD", getResizedImage(R.drawable.tile2_v_door_dark_blue, newSize)),
            Pair("HD", getResizedImage(R.drawable.tile2_h_door_dark_blue, newSize)),
            Pair("V0", getResizedImage(R.drawable.tile2_dark_blue, newSize)),
            Pair("W", getResizedImage(R.drawable.tile2_dark_blue, newSize)),
            Pair("S0", getResizedImage(R.drawable.tile2_dark_blue, newSize)),
            Pair("R", getResizedImage(R.drawable.tile2_border_light_blue, newSize)),
            Pair("C", getResizedImage(R.drawable.tile2_border_light_blue, newSize)),
            Pair("WB", getResizedImage(R.drawable.tile2_dark_blue, newSize)),
//todo fix, and add more
        )
    }//end init tiles

    private fun getResizedImage(image: Int, tileSize: Int): ImageBitmap {
        val newBitmap = BitmapFactory.decodeResource(context.resources, image)

        val matrix = Matrix()
        val scaleHeight = tileSize.toFloat() / newBitmap.height
        val scaleWidth = tileSize.toFloat() / newBitmap.width
        matrix.postScale(scaleWidth, scaleHeight)

        val resizedBitmap =
            Bitmap.createBitmap(newBitmap, 0, 0, newBitmap.width, newBitmap.height, matrix, true)

        return resizedBitmap.asImageBitmap()

    }//end get resized image

    fun updateTileSizeInPx(widthInPx: Int) {
        tileSizeInPx = widthInPx
    }

}//end view model

data class MapUiState(
    val dungeon: Dungeon,
    var clickTile: Offset
)