package uk.ac.tees.mad.SV.Presentation.Screen

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import uk.ac.tees.mad.SV.Presentation.Navigation.SpaceNavigation
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.R
import uk.ac.tees.mad.SV.ui.theme.lobster
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun EditProfileScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val context = LocalContext.current
    val user = viewModel.userData
    val name = remember { mutableStateOf(user.value.name ?: "") }
    val email = remember { mutableStateOf(user.value.email ?: "") }

    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            imageUri.value?.let {
                viewModel.updateProfilePicture(it)
                Toast.makeText(context, "Image Captured: $it", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }

    val cameraPermissionState = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            val uri = createImageUri(context)
            imageUri.value = uri
            launcher.launch(uri!!)
        } else {
            Toast.makeText(context, "Camera permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    Surface(modifier = Modifier.fillMaxSize(), color = colorScheme.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Box(modifier = Modifier.clickable {
                    if (ContextCompat.checkSelfPermission(
                            context, Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        val uri = createImageUri(context)
                        imageUri.value = uri
                        launcher.launch(uri!!)
                    } else {
                        cameraPermissionState.launch(Manifest.permission.CAMERA)
                    }
                }) {
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
                }
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedTextField(value = name.value, onValueChange = { name.value = it })
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(value = email.value, onValueChange = { email.value = it })
                Spacer(modifier = Modifier.height(80.dp))
                Button(
                    onClick = { viewModel.saveUserDetails(name.value) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 70.dp)
                        .height(50.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Save User", fontFamily = lobster)
                }
            }

            Icon(
                imageVector = Icons.Rounded.KeyboardArrowLeft, contentDescription = null, tint = colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(20.dp)
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

private fun createImageUri(context: Context): Uri? {
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val imageFile = File.createTempFile("JPEG_${timeStamp}_", ".jpg", storageDir)

    return try {
        FileProvider.getUriForFile(context, "${context.packageName}.provider", imageFile)
    } catch (e: IllegalArgumentException) {
        e.printStackTrace()
        null
    }
}