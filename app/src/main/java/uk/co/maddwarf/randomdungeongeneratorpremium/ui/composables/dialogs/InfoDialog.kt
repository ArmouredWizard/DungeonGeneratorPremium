package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.dialogs

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun InfoDialog(
    open: Boolean,
    onDismiss: () -> Unit,
    title: String,
    body: String
) {
    Log.d("INFO DIALOG", "$title $body")
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
                Button(
                    onClick = onDismiss,
                ) {
                    Text(text = "CLOSE", style = MaterialTheme.typography.bodyLarge)
                }
            },
            title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
            text = { Text(text = body) }
        )
    }//end ifOpen
}//end InfoDialog
