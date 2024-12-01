package uk.ac.tees.mad.SV.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.ui.theme.lobster

@Composable
fun HomeScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val apod = viewModel.apod
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Scaffold {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), verticalArrangement = Arrangement.Center
            ) {
                if (apod.value != null) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Box {
                            AsyncImage(model = apod.value!!.url, contentDescription = null)
                            Icon(
                                imageVector = Icons.Rounded.FavoriteBorder,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                    Text(
                        text = apod.value!!.title,
                        fontFamily = lobster,
                        fontSize = 22.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Text(text = "Loading", fontSize = 28.sp)
                }
            }
        }
    }
}