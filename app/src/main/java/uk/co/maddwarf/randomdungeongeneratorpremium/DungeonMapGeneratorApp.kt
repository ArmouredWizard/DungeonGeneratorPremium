package uk.co.maddwarf.randomdungeongeneratorpremium

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import uk.co.maddwarf.randomdungeongeneratorpremium.navigation.DungeonMapGeneratorNavHost

@Composable
fun DungeonMapGeneratorApp(
    navController: NavHostController = rememberNavController()
) {
    DungeonMapGeneratorNavHost(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DungeonMapTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    canNavigateHome: Boolean = true,
    navigateToHome: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, style = MaterialTheme.typography.titleLarge) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        actions = {
            if (canNavigateHome) {
                IconButton(onClick = navigateToHome) {
                    Icon(
                        imageVector = Icons.Default.Home, contentDescription = "Home Icon"
                    )
                }
            }
        }//end actions
    )//end CentreAlignedTopBar
}//end DungeonMapTopAppBar

