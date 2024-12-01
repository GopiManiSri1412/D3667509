package uk.ac.tees.mad.SV.Presentation.Screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.rounded.Person
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
fun SignUpScreen(navController: NavHostController, viewModel: SpaceViewModel) {
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val emailCorrect = remember { mutableStateOf(false) }
    val signed = viewModel.signed
    val loading = viewModel.loading
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
    LaunchedEffect(key1 = signed) {
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
                text = "Sign Up",
                fontSize = 28.sp,
                fontFamily = sofadi_one,
                color = colorScheme.primary,
                modifier = Modifier.padding(60.dp)
            )
            Column(modifier = Modifier.padding(40.dp)) {
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
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
                            imageVector = Icons.Rounded.Person,
                            contentDescription = null,
                            tint = colorScheme.primary
                        )
                    },
                    placeholder = { Text(text = "Your Name") },
                )
                Spacer(modifier = Modifier.height(20.dp))
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
                        if (passCorrect.value && name.value.isNotEmpty()) {
                            viewModel.signUp(context, name.value, email.value, password.value)
                        } else {
                            Toast.makeText(
                                context,
                                "Kindly put the credentials correctly!",
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
                            text = "Sign Up",
                            fontFamily = lobster,
                            fontSize = 22.sp,
                            color = colorScheme.background,
                            modifier = Modifier.clickable {
                                navController.navigate(SpaceNavigation.LoginScreen.route)
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.height(30.dp))
                Row {
                    Text(text = "Already User?", fontFamily = sofadi_one, fontSize = 20.sp, color = colorScheme.onSurface)
                    Text(
                        text = " Log in!",
                        fontFamily = lobster,
                        fontSize = 18.sp,
                        color = colorScheme.primary
                    )
                }
            }
        }
    }
}
