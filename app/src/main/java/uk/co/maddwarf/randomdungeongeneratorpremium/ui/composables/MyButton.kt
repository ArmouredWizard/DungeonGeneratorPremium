package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyButton(
    onClick: () -> Unit,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    text: String,
) {
    Button(
        onClick = onClick,
        border = BorderStroke(width = 1.dp, color = Color.Black)
    ) {
        if (leadingIcon != null) {
            Icon(imageVector = leadingIcon, contentDescription = "Leading Icon")
        }
        Text(
            text = text
        )
        if (trailingIcon != null) {
            Icon(imageVector = trailingIcon, contentDescription = "Trailing Icon")
        }
    }//end Button
}//end MyButton