package uk.ac.tees.mad.SV.Presentation.Screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel

@Composable
fun HomeScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    Text(text = "Hie", fontSize = 60.sp)
}