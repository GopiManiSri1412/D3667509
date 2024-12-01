package uk.ac.tees.mad.SV.Presentation.Screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import uk.ac.tees.mad.SV.Presentation.Navigation.SpaceNavigation
import uk.ac.tees.mad.SV.Presentation.Viewmodel.SpaceViewModel
import uk.ac.tees.mad.SV.R
import uk.ac.tees.mad.SV.ui.theme.lobster
import uk.ac.tees.mad.SV.ui.theme.sofadi_one

@Composable
fun LoginScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailCorrect = remember { mutableStateOf(false) }
    val loading = viewModel.loading
    val signed = viewModel.signed
    if (email.value.contains("@gmail.com")) {
        emailCorrect.value = true
    } else {
        emailCorrect.value = false
    }
    val passCorrect = remember {
        mutableStateOf(false)
    }
    var passwordVisible by remember { mutableStateOf(false) }
    val specialCharacters = "!@#$%^&*()-_=+[]{}|;:'\",.<>?/\\"
    if (password.value.length > 6 && password.value.any { it.isUpperCase() } && password.value.any { it in specialCharacters }) {
        passCorrect.value = true
    } else {
        passCorrect.value = false
    }
    LaunchedEffect( signed.value) {
        Log.d("Signed", signed.value.toString())
        if (signed.value) {
            navController.navigate(SpaceNavigation.HomeScreen.route){
                popUpTo(0)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            CloudShape()
            Text(
                text = "Login",
                fontSize = 28.sp,
                fontFamily = sofadi_one,
                color = colorScheme.primary,
                modifier = Modifier.padding(60.dp)
            )
            Column(modifier = Modifier.padding(40.dp)) {
                TextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    colors = TextFieldDefaults.textFieldColors(
                        trailingIconColor = colorScheme.primary,
                        placeholderColor = colorScheme.primary,
                        textColor = colorScheme.primary,
                        backgroundColor = Color.Transparent,
                        cursorColor = colorScheme.primary,
                        focusedIndicatorColor = colorScheme.primary,
                        unfocusedIndicatorColor = colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Email,
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                    },
                    placeholder = { Text(text = "Email address") },
                    trailingIcon = {
                        if (emailCorrect.value) {
                            Image(
                                painter = painterResource(id = R.drawable.checked),
                                contentDescription = null,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                TextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent),
                    colors = TextFieldDefaults.textFieldColors(
                        trailingIconColor = colorScheme.primary,
                        placeholderColor = colorScheme.primary,
                        textColor = colorScheme.primary,
                        backgroundColor = Color.Transparent,
                        cursorColor = colorScheme.primary,
                        focusedIndicatorColor = colorScheme.primary,
                        unfocusedIndicatorColor = colorScheme.primary
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Lock,
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                    },
                    placeholder = { Text(text = "Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.None),
                    trailingIcon = {
                        Row {
                            if (passCorrect.value) {
                                Image(
                                    painter = painterResource(id = R.drawable.checked),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(20.dp)
                                        .align(Alignment.CenterVertically)
                                )
                            }

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                )
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        if (passCorrect.value) {
                            viewModel.logIn(context, email.value, password.value)
                        } else {
                            Toast.makeText(
                                context,
                                "Wrong Password Format",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 1.dp)
                ) {
                    if (loading.value) {
                        CircularProgressIndicator()
                    }else {
                        Text(
                            text = "Log in",
                            fontFamily = lobster,
                            fontSize = 22.sp,
                            color = colorScheme.background
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    Text(text = "New Here?", fontFamily = sofadi_one, fontSize = 20.sp, color = colorScheme.onSurface)
                    Text(
                        text = " Sign up!",
                        fontFamily = lobster,
                        fontSize = 18.sp,
                        color = colorScheme.primary,
                        modifier = Modifier.clickable {
                            navController.navigate(SpaceNavigation.RegisterScreen.route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CloudShape() {
    val cloudColor = MaterialTheme.colorScheme.primary
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .offset(x = (-20).dp, y = 20.dp)
    ) {
        val circleRadius = 40f

        drawCircle(
            color = cloudColor,
            radius = circleRadius,
            center = Offset(x = size.width * 0.3f, y = size.height * 0.5f)
        )
        drawCircle(
            color = cloudColor,
            radius = circleRadius * 1.2f,
            center = Offset(x = size.width * 0.5f, y = size.height * 0.4f)
        )
        drawCircle(
            color = cloudColor,
            radius = circleRadius * 0.9f,
            center = Offset(x = size.width * 0.7f, y = size.height * 0.5f)
        )
        drawCircle(
            color = cloudColor,
            radius = circleRadius * 0.8f,
            center = Offset(x = size.width * 0.4f, y = size.height * 0.65f)
        )
        drawCircle(
            color = cloudColor,
            radius = circleRadius * 0.7f,
            center = Offset(x = size.width * 0.6f, y = size.height * 0.65f)
        )
    }
}