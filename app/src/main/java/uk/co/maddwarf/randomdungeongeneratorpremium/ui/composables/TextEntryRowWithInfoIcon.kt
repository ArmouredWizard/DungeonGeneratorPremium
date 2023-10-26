package uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.dialogs.InfoDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextEntryRowWithInfoIcon(
    data: String,
    onValueChange: (String) -> Unit,
    label: String,
    infoText: String,
    singleLine: Boolean = true,
    width: Float = 0.9f,
    limited: Boolean = true,
    next: ImeAction = ImeAction.Next,
    required: Boolean = false
) {
    val showDialog = remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(width),
            value = data,
            onValueChange = onValueChange,
            label = { Text(text = label) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant, //todo fix these colours
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            singleLine = singleLine,
            supportingText = {
                /* if (limited) {
                     Text(
                         text = "${data.length} / 19",
                         modifier = Modifier.fillMaxWidth(),
                         textAlign = TextAlign.End,
                     )
                 }*/
                if (required) {
                    Text(
                        text = "*Required",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                imeAction = next
            )
        )//end OutlinedTextField
        Icon(
            imageVector = Icons.Default.Info,
            contentDescription = "Info",
            modifier = Modifier
                .padding(5.dp)
                .clickable(
                    onClick = { showDialog.value = true }
                )
        )//end Icon
    }
    if (showDialog.value) {
        InfoDialog(
            open = showDialog.value,
            onDismiss = { showDialog.value = false },
            title = label,
            body = infoText
        )
    }
}//end TextEntryWithInfoIcon

@Composable
fun TextEntryWithSpinner(
    textValue: String,
    label: String,
    infoText: String,
    onValueChange: (String) -> Unit = {},
    itemList: List<String>,
    limited: Boolean = true
) {
    var spinnerExpanded by remember { mutableStateOf(false) }
    var chosenItem by remember {
        mutableStateOf(
            "Default"
        )
    }

    fun itemChooser(item: String) {
        spinnerExpanded = false
        chosenItem = item
        onValueChange(chosenItem)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MySpinner(
            expanded = spinnerExpanded,
            onClick = { spinnerExpanded = !spinnerExpanded },
            list = itemList,
            chooser = ::itemChooser,
            report = "" //chosenItem
        )
        TextEntryRowWithInfoIcon(
            data = textValue,
            onValueChange = { if (it.length <= 19 || !limited) onValueChange(it) },
            label = label,
            infoText = infoText,
            width = 0.85f,
            limited = limited
        )
    }
}//end TextEntryWithSpinner
