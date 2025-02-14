package uk.ac.tees.mad.SV.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
import uk.ac.tees.mad.SV.Presentation.Navigation.SpaceNavigation
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.ui.theme.lobster
import uk.ac.tees.mad.SV.ui.theme.sofadi_one

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val apod = viewModel.apod
    val isRefreshing = viewModel.loading.value
    var isApodFavorited by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(apod.value?.title) {
        if (apod.value != null) {
            val apodInDb = viewModel.getApodByTitle(apod.value!!.title)
            isApodFavorited = apodInDb != null
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row {
                            Box(
                                modifier = Modifier
                                    .size(45.dp)
                                    .clip(CircleShape)
                                    .background(colorScheme.primary)
                                    .clickable {
                                        navController.navigate(SpaceNavigation.ProfileScreen.route)
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Person,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(35.dp),
                                    tint = Color.White
                                )
                            }
                            Text(
                                text = "Astronomical picture of the Day",
                                fontFamily = sofadi_one,
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        ) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    viewModel.fetchApi()
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(it), verticalArrangement = Arrangement.Center
                ) {
                    if (apod.value != null) {

                        Box(
                            modifier = Modifier
                                .padding(12.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            AsyncImage(
                                model = apod.value!!.url,
                                contentDescription = null,
                                modifier = Modifier
                                    .clickable {
                                        navController.navigate(SpaceNavigation.ImageDetailScreen.route)
                                    }
                                    .fillMaxWidth(),
                                contentScale = ContentScale.Crop
                            )
                            Icon(
                                imageVector = if (isApodFavorited) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                contentDescription = null,
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(8.dp)
                                    .size(40.dp)
                                    .clickable {
                                        coroutineScope.launch {
                                            viewModel.insertIfNotExists(apod.value!!)
                                            val apodInDb =
                                                viewModel.getApodByTitle(apod.value!!.title)
                                            isApodFavorited = apodInDb != null
                                        }
                                    },
                                tint = Color.White
                            )
                        }

                        Text(
                            text = apod.value!!.title,
                            fontFamily = lobster,
                            fontSize = 22.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            LinearProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}