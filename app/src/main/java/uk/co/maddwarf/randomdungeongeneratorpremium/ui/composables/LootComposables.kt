package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Art
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Gem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Loot
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
    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "${content.size} Gems worth ${content[0].value}GP each")
        TextButton(onClick = { gemExpanded = !gemExpanded }) {
            if (gemExpanded) {
                Text(text = "Hide")
            } else {
                Text(text = "Show...")
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
        Text(text = "${content.size} Art Items worth ${content[0].value}GP each")
        TextButton(onClick = { artExpanded = !artExpanded }) {
            if (artExpanded) {
                Text(text = "Hide")
            } else {
                Text(text = "Show...")
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