package uk.co.maddwarf.randomdungeongeneratorpremium.model

import android.graphics.Rect
import androidx.compose.ui.geometry.Offset
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.OddifyUseCase.evenify
import uk.co.maddwarf.randomdungeongeneratorpremium.domain.OddifyUseCase.oddify

data class Room(
    val index: Int,
    var rectangle: Rect,
    var connected: Boolean = false,
    var contents: String = ""
)

fun Room.getOddCenter(): Offset {
    return Offset(
        (oddify((rectangle.left + rectangle.right) / 2).toFloat()),
        (oddify((rectangle.top + rectangle.bottom) / 2).toFloat())
    )
}

fun Room.getEvenCenter(): Offset {
    return Offset(
        (evenify((rectangle.left + rectangle.right) / 2).toFloat()),
        (evenify((rectangle.top + rectangle.bottom) / 2).toFloat())
    )
}

fun Room.getCenter(): Offset {
    return Offset(
        x = ((rectangle.left + rectangle.right) / 2).toFloat(),
        y = ((rectangle.top + rectangle.bottom) / 2).toFloat()
    )
}

fun Room.getDimensions(): String {
    val width = ((rectangle.right + 1) - rectangle.left) * 5
    val height = ((rectangle.bottom + 1) - rectangle.top) * 5
    return "$width ft x $height ft"
}

