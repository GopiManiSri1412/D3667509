package uk.ac.tees.mad.SV.Presentation.Screen

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.SV.Presentation.Navigation.SpaceNavigation
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.ui.theme.lobster
import uk.ac.tees.mad.SV.ui.theme.sofadi_one

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val favoriteApodList = viewModel.apodOffline.collectAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Scaffold(topBar = {
            androidx.compose.material3.TopAppBar(
                title = {
                    Row {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { navController.navigateUp() }
                        )
                        Text(text = "Favorite Section", fontFamily = lobster, modifier = Modifier.align(Alignment.CenterVertically).padding(start = 30.dp))
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier.background(Color.Transparent)
            )
        }) {
            if (favoriteApodList.value != null) {
                LazyColumn(modifier = Modifier.padding(it)) {
                    items(favoriteApodList.value!!) { items ->
                        showCardView(
                            image = items.url,
                            title = items.title,
                            date = items.date,
                            onItemClick = {
                                val encodedTitle = Uri.encode(items.title)
                                navController.navigate("favorite_details_screen/$encodedTitle")
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun showCardView(image: String, title: String, date: String, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                onItemClick(title)
            }
    ) {
        Column {
            AsyncImage(model = image, contentDescription = null)
            Text(
                text = title,
                fontSize = 20.sp,
                fontFamily = sofadi_one,
                modifier = Modifier.padding(4.dp)
            )
            Text(text = date, modifier = Modifier.padding(horizontal = 4.dp))
        }
    }
}