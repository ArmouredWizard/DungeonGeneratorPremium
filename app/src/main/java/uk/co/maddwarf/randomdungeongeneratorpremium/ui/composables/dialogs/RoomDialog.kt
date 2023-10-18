package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.dialogs

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Room
import uk.co.maddwarf.randomdungeongeneratorpremium.model.getDimensions

@Composable
fun RoomDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    room: Room
) {
    Log.d("INFO DIALOG", "$room")
    if (open) {
        AlertDialog(
            modifier = Modifier
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(20.dp)
                ),
            onDismissRequest = onDismiss,
            confirmButton = {
             /*   Button(
                    onClick = onDismiss,
                ) {
                    Text(text = "CLOSE", style = MaterialTheme.typography.bodyLarge)
                }*/
            },
            title = {

                Text(
                    text = "Room ${room.index}",
                    style = MaterialTheme.typography.titleLarge,
                )

            },
            text = {
                Column (
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Dimensions: " + room.getDimensions())
                    }
                    Text(text = room.contents)
                }
            }
        )
    }//end ifOpen
}//end InfoDialog


