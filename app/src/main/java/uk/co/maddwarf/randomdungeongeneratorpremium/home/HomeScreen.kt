package uk.co.maddwarf.randomdungeongeneratorpremium.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import uk.co.maddwarf.randomdungeongeneratorpremium.DungeonMapTopAppBar
import uk.co.maddwarf.randomdungeongeneratorpremium.R
import uk.co.maddwarf.randomdungeongeneratorpremium.navigation.NavigationDestination
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.MyButton
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.composables.MySpinner

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    navigateToMapScreen: (String) -> Unit,
    navigateToSettings: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val sizeList = homeViewModel.sizeList
    val inhabitantsList = homeViewModel.inhabitantsList


    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            DungeonMapTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false,
                scrollBehavior = scrollBehavior,
                canNavigateHome = false
            )
        },
     /*   floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToSettings,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings Icon"
                )
            }
        }*/
    ) { innerPadding ->
        HomeBody(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            navigateToMapScreen = navigateToMapScreen,
            sizeList = sizeList,
            inhabitantsList = inhabitantsList
        )
    }//end Scaffold

}//end HomeScreen


@Composable
fun HomeBody(
    modifier: Modifier,
    navigateToMapScreen: (String) -> Unit,
    sizeList: List<String>,
    inhabitantsList: List<String>
) {
    Log.d("HOME BODY", "Loaded")
    var levelList = mutableListOf<String>()
    for (i in 1..20) {
        levelList.add(i.toString())
    }


    var sizeExpanded by remember { mutableStateOf(false) }
    var chosenSize by remember { mutableStateOf(sizeList[0]) }
    fun sizeChooser(size: String) {
        sizeExpanded = false
        chosenSize = size
        // navigateToMapScreen(chosenSize)
    }

    var layersExpanded by remember { mutableStateOf(false) }
    var chosenLayers by remember { mutableStateOf(levelList[0]) }
    fun layersChooser(size: String) {
        layersExpanded = false
        chosenLayers = size
        // navigateToMapScreen(chosenLayers)
    }

    var inhabitantsExpanded by remember { mutableStateOf(false) }
    var chosenInhabitants by remember { mutableStateOf(inhabitantsList[0]) }
    fun inhabitantsChooser(inhabitants: String) {
        inhabitantsExpanded = false
        chosenInhabitants = inhabitants
    }


    var levelExpanded by remember { mutableStateOf(false) }
    var chosenLevel by remember { mutableStateOf(levelList[0]) }
    fun levelChooser(level: String) {
        levelExpanded = false
        chosenLevel = level
    }
    var pcNumbersExpanded by remember { mutableStateOf(false) }
    var chosenPcNumbers by remember { mutableStateOf(levelList[0]) }
    fun pcNumbersChooser(pcNumbers: String) {
        pcNumbersExpanded = false
        chosenPcNumbers = pcNumbers
    }



    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
              //  .background(color = Color.Gray)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically

        ) {
            //Spacer(modifier = Modifier.height(60.dp))


            Column(
                modifier = Modifier.fillMaxSize()
                    .weight(1f)
                   // .background(color = Color.DarkGray),
                ,verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End

            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Level Size: ")
                    MySpinner(
                        expanded = sizeExpanded,
                        onClick = { sizeExpanded = !sizeExpanded },
                        list = sizeList,
                        chooser = ::sizeChooser,
                        report = chosenSize
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Number of Layers: ")
                    MySpinner(
                        expanded = layersExpanded,
                        onClick = { layersExpanded = !layersExpanded },
                        list = levelList,
                        chooser = ::layersChooser,
                        report = chosenLayers
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Inhabitants: ")
                    MySpinner(
                        expanded = inhabitantsExpanded,
                        onClick = { inhabitantsExpanded = !inhabitantsExpanded },
                        list = inhabitantsList,
                        chooser = ::inhabitantsChooser,
                        report = chosenInhabitants
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().weight(1f)
                 //   .background(color = Color.LightGray),
               , verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "PC Level: ")
                    MySpinner(
                        expanded = levelExpanded,
                        onClick = { levelExpanded = !levelExpanded },
                        list = levelList,
                        chooser = ::levelChooser,
                        report = chosenLevel
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Number of PCs: ")
                    MySpinner(
                        expanded = pcNumbersExpanded,
                        onClick = { pcNumbersExpanded = !pcNumbersExpanded },
                        list = levelList,
                        chooser = ::pcNumbersChooser,
                        report = chosenPcNumbers
                    )
                }
            }//end Column

        }//end outer row
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
        ){
        MyButton(
            text = "Make the Map!",
            onClick = { navigateToMapScreen("$chosenSize,$chosenInhabitants,$chosenLevel,$chosenLayers,$chosenPcNumbers") },
        )
        }
    }//end Box
}
