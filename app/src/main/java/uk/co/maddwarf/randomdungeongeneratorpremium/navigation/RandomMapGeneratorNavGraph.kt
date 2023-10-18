package uk.co.maddwarf.randomdungeongeneratorpremium.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import uk.co.maddwarf.randomdungeongeneratorpremium.home.HomeDestination
import uk.co.maddwarf.randomdungeongeneratorpremium.home.HomeScreen
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.screens.MapScreen
import uk.co.maddwarf.randomdungeongeneratorpremium.ui.screens.MapScreenDestination

@Composable
fun DungeonMapGeneratorNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val startSize = "Small"
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        //  startDestination = MapScreenDestination.route,
        modifier = modifier
    ) {

        fun navigateToHome() = navController.navigate(HomeDestination.route)

        composable(route = HomeDestination.route) {
            HomeScreen(
                modifier = Modifier,
                navigateToMapScreen = { navController.navigate("${MapScreenDestination.route}/${it}") },
                navigateToSettings = {},//todo add Settings destination

            )
        }//end Home composable

        composable(route = MapScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(MapScreenDestination.itemIdArg) {
                    type = NavType.StringType
                }
            )
        ) {
            MapScreen(
                navigateToHome = { navigateToHome() }
            )
        }

        /*
                composable(route = NpcDisplayDestination.route) {
                    NpcDisplayScreen(
                        //  navigateToHome = { navigateToHome() },
                    )

                }//end  Display screen
        */

    }//end NavHost
}//end DitDNavHost