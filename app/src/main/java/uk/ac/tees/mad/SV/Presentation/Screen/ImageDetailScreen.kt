package uk.ac.tees.mad.SV.Presentation.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
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
fun ImageDetailScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val apod = viewModel.apod
    androidx.compose.material3.Surface(
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
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Transparent, // Transparent background
                        titleContentColor = Color.White // Adjust text/icon color for contrast
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
                Card(modifier = Modifier.padding(horizontal = 12.dp, vertical = 50.dp)) {
                    Column {
                        AsyncImage(model = apod.value!!.hdurl, contentDescription = null)
                        Text(
                            text = apod.value!!.title,
                            fontSize = 22.sp,
                            fontFamily = lobster,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(
                            text = apod.value!!.date,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                        Text(text = apod.value!!.explanation, modifier = Modifier.padding(4.dp))
                        Button(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
                            androidx.compose.material3.Text(text = "Save to Device")
                        }
                    }
                }
            }
        }
    }
}