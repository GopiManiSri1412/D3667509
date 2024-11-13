package uk.ac.tees.mad.SV.Presentation.Navigation

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.SV.Presentation.Screen.SplashScreen

@Composable
fun SpaceNavGraph(){
    val navController = rememberNavController()
    Surface {
        NavHost(
            navController = navController,
            startDestination = SpaceNavigation.SplashScreen.route
        ) {
            composable(route = SpaceNavigation.SplashScreen.route){
                SplashScreen(navController = navController)
            }
        }
    }
}