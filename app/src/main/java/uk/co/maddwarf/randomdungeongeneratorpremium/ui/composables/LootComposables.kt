package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Art
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Gem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Loot
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Magic
import uk.co.maddwarf.randomdungeongeneratorpremium.model.MundaneItem

@Composable
fun CashRow(content: Loot) {
    Row(
        modifier = Modifier
            .fillMaxWidth(.8f),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (content.coins.pp > 0) Text(text = "${content.coins.pp}PP")
        if (content.coins.gp > 0) Text(text = "${content.coins.gp}GP")
        if (content.coins.ep > 0) Text(text = "${content.coins.ep}EP")
        if (content.coins.sp > 0) Text(text = "${content.coins.sp}SP")
        if (content.coins.cp > 0) Text(text = "${content.coins.cp}CP")
    }
}//end cash row

@Composable
fun GemsList(content: List<Gem>) {
    var gemExpanded by remember { mutableStateOf(false) }
    val showText = if (gemExpanded) {
        "Hide"
    } else {
        "Show"
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${content.size} Gems worth ${content[0].value}GP each",
                Modifier.weight(1f)
            )
            TextButton(onClick = { gemExpanded = !gemExpanded }, Modifier.weight(.5f)) {
                if (gemExpanded) {
                    Text(text = "Hide")
                } else {
                    Text(text = "Show...")
                }
            }
        }
        if (gemExpanded) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                content.forEach { it ->
                    Text(text = it.name)
                }
            }
        }
    }
}//end GemsList

@Composable
fun ArtsList(content: List<Art>) {
    var artExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Row() {
            Text(
                text = "${content.size} Art Items worth ${content[0].value}GP each",
                Modifier.weight(1f)
            )
            TextButton(onClick = { artExpanded = !artExpanded }, Modifier.weight(0.5f)) {
                if (artExpanded) {
                    Text(text = "Hide")
                } else {
                    Text(text = "Show...")
                }
            }
        }
        if (artExpanded) {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                content.forEach { it ->
                    Text(text = it.name)
                }
            }
        }
    }
}//end Art List

@Composable
fun MagicsList(content: List<Magic>) {
    var magicExpanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Row() {
            Text(
                text = "${content.size} Magical Items",
                Modifier.weight(1f)
            )
            TextButton(onClick = { magicExpanded = !magicExpanded }, Modifier.weight(0.5f)) {
                if (magicExpanded) {
                    Text(text = "Hide")
                } else {
                    Text(text = "Show...")
                }
            }
        }
        if (magicExpanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                //  horizontalAlignment = Alignment.Start
            ) {
                content.forEach { it ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = it.name)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(text = "(${it.description})")
                    }

                }
            }
        }
    }
}//end Art List