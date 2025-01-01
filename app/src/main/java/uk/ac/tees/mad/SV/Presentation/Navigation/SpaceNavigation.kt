package uk.ac.tees.mad.SV.Presentation.Navigation

sealed class SpaceNavigation(val route : String) {
    object SplashScreen : SpaceNavigation("splash_screen")
    object LoginScreen : SpaceNavigation("login_screen")
    object RegisterScreen : SpaceNavigation("register_screen")
    object HomeScreen : SpaceNavigation("home_screen")
    object ImageDetailScreen : SpaceNavigation("image_detail_screen")
    object ProfileScreen : SpaceNavigation("profile_screen")
}