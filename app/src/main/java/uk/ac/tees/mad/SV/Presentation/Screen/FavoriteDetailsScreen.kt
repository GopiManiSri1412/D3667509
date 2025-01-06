package uk.ac.tees.mad.SV.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.ui.theme.lobster

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteDetailsScreen(title : String, navController: NavHostController, viewModel: SpaceViewModel) {
    val apod = viewModel.findByTitle(title)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowLeft,
                            contentDescription = null,
                            modifier = Modifier.size(40.dp)
                                .clickable { navController.navigateUp() }
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = Color.Transparent,
                    ),
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                if (apod!=null) {
                    Card(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 20.dp)
                            .verticalScroll(
                                rememberScrollState()
                            )
                    ) {
                        Column {
                            AsyncImage(model = apod!!.hdurl, contentDescription = null)
                            Text(
                                text = apod!!.title,
                                fontSize = 22.sp,
                                fontFamily = lobster,
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(
                                text = apod!!.date,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                            Text(text = apod!!.explanation, modifier = Modifier.padding(4.dp))
                            Button(onClick = {
                                viewModel.deleteFromDb(apod)
                                             navController.navigateUp()
                                             }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(Color.Red)) {
                                androidx.compose.material3.Text(text = "Delete")
                            }
                        }
                    }
                }else{
                    Text(text = "No data found")
                }
            }
        }
    }
}