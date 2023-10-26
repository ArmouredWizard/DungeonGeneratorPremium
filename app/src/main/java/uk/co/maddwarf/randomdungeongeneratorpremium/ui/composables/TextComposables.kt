package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TitleBlock(
    title: String,
    text: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(10.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$title $text",
            style = MaterialTheme.typography.titleLarge,
        )
    }
}//end TitleBlock

@Composable
fun BodyBlock(
    title: String = "",
    text: String,
    width: Float = 0.95f
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(width)
            .clip(shape = RoundedCornerShape(15.dp))
            .padding(10.dp)
    ) {
        Text(text = text)
    }
}//end BodyBlock

@Composable
fun TraitText(
    title: String,
    text: String,
    onClick: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .border(width = 2.dp, color = Color.DarkGray, shape = RoundedCornerShape(10.dp))
            .clip(shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .clickable { onClick(text) }
        ) {
            Text(text = title, style = MaterialTheme.typography.bodySmall)
            Text(text = text)
        }
    }
}//end TraitText

@Composable
fun WideTraitText(
    title: String,
    text: String,
    onClick: (String) -> Unit = {},
) {
    Surface(
        modifier = Modifier
            .padding(5.dp)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(shape = RoundedCornerShape(10.dp))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .padding(10.dp)
                .clickable { onClick(text) }
                .fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold
            )
            Text(text = text)
        }
    }
}//end WideTraitText
