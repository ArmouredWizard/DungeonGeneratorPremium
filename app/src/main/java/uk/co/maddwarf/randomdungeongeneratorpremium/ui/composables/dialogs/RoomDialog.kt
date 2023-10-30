package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.dialogs

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Art
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Empty
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Gem
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Hazard
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Loot
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Monsters
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Obstacle
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Room
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trap
import uk.co.maddwarf.randomdungeongeneratorpremium.model.Trick
import uk.co.maddwarf.randomdungeongeneratorpremium.model.getDimensions
import uk.co.maddwarf.randomdungeongeneratorpremium.model.totalXp
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.ArtsList
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.CashRow
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.GemsList
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.MagicsList


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
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Dimensions: " + room.getDimensions())
                    }

                    for (content in room.contents) {
                        when (content) {
                            is Monsters -> {
                                for (monster in content.monsters) {
                                    Text(text = "${monster.value}x ${monster.key.name}")
                                }
                                Text(text = "Total XP: ${content.totalXp()}")
                            }

                            is Obstacle -> {
                                Text(text = "${content.name}")
                                Text(text = "${content.description}")
                            }

                            is Trap -> {
                                Text(text = "${content.name}")
                                Text(text = "${content.description}")
                                Text(text = "Triggered when something is ${content.trigger.name}")

                            }

                            is Trick -> {
                                Text(text = "${content.name}")
                                Text(text = "${content.description}")
                                Text(text = "Item: ${content.trickItem.name}")

                            }

                            is Hazard -> {
                                Text(text = "${content.name}")
                                Text(text = "${content.description}")
                            }

                            is Loot -> {
                                //cash
                                CashRow(content)

                                //mundane items
                                if (content.gemList.isNotEmpty()) GemsList(content.gemList)
                                if (content.artList.isNotEmpty()) ArtsList(content.artList)

                                //todo lots of work to do here
                                //magic items
                                if (content.magicItemsList.isNotEmpty()) MagicsList(content.magicItemsList)


                            }

                            is Empty -> {
                                Text(text = "${content.name}")
                            }

                            else -> {
                                Text(text = content.toString())
                            }
                        }
                    }
                }//end column
            }
        )
    }//end ifOpen
}//end InfoDialog




