package uk.co.maddwarf.randomdungeongeneratorpremium.ui.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.maddwarf.randomdungeongeneratorpremium.R
import uk.co.maddwarf.randomdungeongeneratorpremium.navigation.NavigationDestination
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Dungeon
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Room
import uk.co.maddwarf.randomdungeongeneratorpremium.model.getCenter
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.dialogs.RoomDialog

object MapScreenDestination : NavigationDestination {
    override val route = "map_screen"
    override val titleRes = R.string.map_screen_title

    const val itemIdArg = "map_data"
    val routeWithArgs = "$route/{$itemIdArg}"
}

@Composable
fun MapScreen(
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit,
    mapViewModel: MapViewModel = hiltViewModel()
) {
    val uiState by mapViewModel.uiState.collectAsState()

    fun updateScreenSizePx(width: Float, height: Float) {
        mapViewModel.updateScreenSizePx(width = width, height = height)
    }

    val configuration = LocalConfiguration.current

    val density = LocalDensity.current
    val widthInDp = configuration.screenWidthDp
    val heightInDp = configuration.screenHeightDp

    val widthInPx = with(density) { widthInDp.dp.roundToPx() }
    val heightInPx = with(density) { heightInDp.dp.roundToPx() }

    val tileSizeInPx = mapViewModel.tileSizeInPx
    mapViewModel.updateTileSizeInPx(tileSizeInPx)

    if (tileSizeInPx == 0) {
        Log.d("UPDATING SIZE", "width: $widthInPx")
        updateScreenSizePx(width = widthInPx.toFloat(), height = heightInPx.toFloat())
        mapViewModel.makeMap()
    }

    val dungeon = uiState.dungeon
    val roomList = dungeon.roomList

    val tileMap: HashMap<String, ImageBitmap> = if (tileSizeInPx == 0) {
        hashMapOf()
    } else {
        mapViewModel.initTileMap()
    }

    val clickTile = uiState.clickTile

    var roomDialogExpanded by remember { mutableStateOf(false) }
    fun roomDialogDismiss() {
        roomDialogExpanded = false
    }

    fun doCanvasClick(clicked: Offset) {
        Log.d("CLCIKED", clicked.toString())
        mapViewModel.doCanvasClick(clicked)
        roomDialogExpanded = !roomDialogExpanded
    }

    val clickedRoom = mapViewModel.clickedRoom

    fun refresh() {
        mapViewModel.refreshMap(mapViewModel.mapSize)
    }

    MapBody(
        roomList = roomList,
        tileSizeInPx = tileSizeInPx,
        tileMap = tileMap,
        dungeon = dungeon,
        modifier = Modifier
            .fillMaxSize(),
        navigateToHome = navigateToHome,
        doCanvasClick = { doCanvasClick(it) },
        clickTile = clickTile,
        roomDialogExpanded = roomDialogExpanded,
        roomDialogDismiss = { roomDialogDismiss() },
        clickedRoom = clickedRoom,
        updateScreenSizePx = { fl: Float, fl1: Float -> updateScreenSizePx(fl, fl1) },
        refresh = { refresh() }
    )

}//end Map Screen

@Composable
fun MapBody(
    roomList: List<Room>,
    tileSizeInPx: Int,
    updateScreenSizePx: (Float, Float) -> Unit,
    dungeon: Dungeon,
    modifier: Modifier,
    tileMap: HashMap<String, ImageBitmap>,
    navigateToHome: () -> Unit,
    doCanvasClick: (Offset) -> Unit,
    clickTile: Offset,
    roomDialogExpanded: Boolean,
    roomDialogDismiss: () -> Unit,
    clickedRoom: Room,
    refresh: () -> Unit
) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.tertiaryContainer)
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { doCanvasClick(it) }
                )
            }
    ) {

        val canvasSize = this.size
        Log.d("CANVAS", "h: ${canvasSize.height}. w: ${canvasSize.width}")

        updateScreenSizePx(canvasSize.width, canvasSize.height)

        drawMatrix(tileSizeInPx = tileSizeInPx, matrix = dungeon.map, tileMap = tileMap)

        drawRoomRectangles(roomList = roomList, tileSizeInPx = tileSizeInPx)
        drawRoomNumbers(roomList, textMeasurer, tileSizeInPx)

        drawClickTile(clickTile, tileSizeInPx)

    }//end canvas

    if (clickedRoom.index > 0) {
        RoomDialog(
            open = roomDialogExpanded,
            onDismiss = { roomDialogDismiss() },
            room = clickedRoom
        )
    }

    Row(
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.End
    ) {
        NavRail(
            navigateToHome = navigateToHome,
            refresh = refresh
        )
    }

}//end Map Body

fun DrawScope.drawRoomRectangles(roomList: List<Room>, tileSizeInPx: Int) {
    for (room in roomList) {
        val rectWidth = (room.rectangle.right + 1 - room.rectangle.left) * tileSizeInPx
        val rectHeight = (room.rectangle.bottom + 1 - room.rectangle.top) * tileSizeInPx
        drawRect(
            color = Color.Blue,
            topLeft = Offset(
                (room.rectangle.left * tileSizeInPx).toFloat(),
                (room.rectangle.top * tileSizeInPx).toFloat()
            ),
            size = Size(height = rectHeight.toFloat(), width = rectWidth.toFloat()),
            style = Stroke(width = 2f)
        )
    }
}//end draw Room rectangles

fun DrawScope.drawClickTile(clickTile: Offset, tileSizeInPx: Int) {
    drawCircle(color = Color.Red, radius = 10f, center = clickTile * tileSizeInPx.toFloat())
    Log.d("CLICKTILE", clickTile.toString())
}

@Composable
fun NavRail(
    navigateToHome: () -> Unit,
    refresh: () -> Unit
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Two", "Three", "Refresh")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.Add,
        Icons.Default.AddCircle,
        Icons.Default.Refresh
    )

    fun doNavRailClick(index: Int) {
        selectedItem = index
        when (items[selectedItem]) {
            "Home" -> {
                Log.d("NAVCLICK", "ONE")
                navigateToHome()
            }

            "Two" -> {}
            "Refresh" -> {
                refresh()
            }
        }
    }//end do navRailClick

    NavigationRail(
        modifier = Modifier.wrapContentWidth()
    ) {
        items.forEachIndexed { index, item ->
            NavigationRailItem(
                selected = selectedItem == index,
                onClick = { doNavRailClick(index) },
                icon = { Icon(icons[index], contentDescription = "ICON $index") },
                label = { Text(text = item) }
            )
        }
    }
}//end NavRail

fun DrawScope.drawRoomNumbers(
    roomList: List<Room>,
    textMeasurer: TextMeasurer,
    tileSize: Int
) {
    for (room in roomList) {
        drawText(
            textMeasurer = textMeasurer,
            text = room.index.toString(),
            style = TextStyle(fontWeight = FontWeight.Bold),
            topLeft = (room.getCenter() * tileSize.toFloat()) + Offset(5f, 0f)
        )
    }
}//end Draw Room Numbers

fun DrawScope.drawMatrix(
    tileSizeInPx: Int,
    matrix: Array<Array<String>>,
    tileMap: HashMap<String, ImageBitmap>
) {
    for (y in (matrix.indices)) {
        for (x in (matrix[y].indices)) {
            drawImage(
                image = tileMap[matrix[y][x]]!!,
                topLeft = Offset((x * tileSizeInPx).toFloat(), (y * tileSizeInPx).toFloat())
            )
        }//end inner loop
    }//end outer loop
}//end Draw Matrix