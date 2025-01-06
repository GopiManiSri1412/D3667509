package uk.ac.tees.mad.SV.Presentation.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import uk.ac.tees.mad.SV.Presentation.Navigation.SpaceNavigation
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.R
import uk.ac.tees.mad.SV.ui.theme.lobster
import uk.ac.tees.mad.SV.ui.theme.sofadi_one

@Composable
fun ProfileScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val user = viewModel.userData
    Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (user.value.profilePictureUrl.isNotEmpty()) {
                    AsyncImage(
                        model = user.value.profilePictureUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(1.dp, colorScheme.primary, CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.boy),
                        contentDescription = null,
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .border(1.dp, colorScheme.primary, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.size(20.dp))
                Text(text = user.value.name, fontSize = 22.sp, fontFamily = lobster)
                Text(text = user.value.email, fontFamily = sofadi_one)
                Row(modifier = Modifier
                    .padding(horizontal = 80.dp, vertical = 20.dp)
                    .clickable {
                        navController.navigate(SpaceNavigation.EditProfileScreen.route)
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.Edit,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Edit Profile",
                        fontSize = 18.sp,
                        fontFamily = sofadi_one,
                        color = colorScheme.tertiary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = colorScheme.primary
                    )
                }
                Row(modifier = Modifier
                    .padding(horizontal = 80.dp, vertical = 20.dp)
                    .clickable {
                        navController.navigate(SpaceNavigation.FavoriteScreen.route)
                    }) {
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Favorite Section",
                        fontSize = 18.sp,
                        fontFamily = sofadi_one,
                        color = colorScheme.tertiary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = colorScheme.primary
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 80.dp, vertical = 20.dp)
                        .clickable {
                            navController.navigate(SpaceNavigation.LoginScreen.route) {
                                popUpTo(0)
                                viewModel.signOut()
                            }
                        }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Logout,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Log out",
                        fontSize = 18.sp,
                        fontFamily = sofadi_one,
                        color = colorScheme.tertiary,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Rounded.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier.align(Alignment.CenterVertically),
                        tint = colorScheme.primary
                    )
                }
            }
            Icon(imageVector = Icons.Rounded.KeyboardArrowLeft,
                contentDescription = null,
                tint = colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(vertical = 40.dp)
                    .size(40.dp)
                    .clickable {
                        navController.navigate(SpaceNavigation.HomeScreen.route) {
                            popUpTo(0)
                        }
                    }
            )
        }
    }
}