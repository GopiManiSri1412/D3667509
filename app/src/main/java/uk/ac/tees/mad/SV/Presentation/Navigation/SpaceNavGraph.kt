package uk.ac.tees.mad.SV.Presentation.Navigation

import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uk.ac.tees.mad.SV.Presentation.Screen.HomeScreen
import uk.ac.tees.mad.SV.Presentation.Screen.ImageDetailScreen
import uk.ac.tees.mad.SV.Presentation.Screen.LoginScreen
import uk.ac.tees.mad.SV.Presentation.Screen.ProfileScreen
import uk.ac.tees.mad.SV.Presentation.Screen.SignUpScreen
import uk.ac.tees.mad.SV.Presentation.Screen.SplashScreen
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel

@Composable
fun SpaceNavGraph(){
    val navController = rememberNavController()
    val viewModel : SpaceViewModel = viewModel()
    Surface {
        NavHost(
            navController = navController,
            startDestination = SpaceNavigation.SplashScreen.route
        ) {
            composable(route = SpaceNavigation.SplashScreen.route){
                SplashScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = SpaceNavigation.LoginScreen.route){
                LoginScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = SpaceNavigation.RegisterScreen.route){
               SignUpScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = SpaceNavigation.HomeScreen.route){
                HomeScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = SpaceNavigation.ImageDetailScreen.route){
                ImageDetailScreen(navController = navController, viewModel = viewModel)
            }
            composable(route = SpaceNavigation.ProfileScreen.route){
                ProfileScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}