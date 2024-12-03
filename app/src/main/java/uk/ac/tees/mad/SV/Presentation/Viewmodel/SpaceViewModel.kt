package uk.ac.tees.mad.SV.Presentation.Viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.Authentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uk.ac.tees.mad.SV.Data.Remote.Apod
import uk.ac.tees.mad.SV.Data.Remote.NasaApi
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(
    private val authentication: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val api: NasaApi
) : ViewModel() {

    val loading = mutableStateOf(false)
    val signed = mutableStateOf(false)
    val apod = mutableStateOf<Apod?>(null)

    init {
        if (authentication.currentUser != null) {
            signed.value = true
            getUserDetails()
            fetchApi()
        }
    }

    fun signUp(context: Context, name: String, email: String, password: String) {
        loading.value = true
        authentication.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            val user = hashMapOf(
                "name" to name,
                "email" to email,
                "password" to password
            )
            firestore.collection("users").document(email).set(user).addOnSuccessListener {
                loading.value = false
                signed.value = true
                getUserDetails()
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                loading.value = false
                Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            loading.value = false
            Toast.makeText(context, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun logIn(context: Context, email: String, password: String) {
        loading.value = true
        authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loading.value = false
            signed.value = true
            getUserDetails()
            Toast.makeText(context, "Log in successful", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            loading.value = false
            Toast.makeText(context, "Log in failed", Toast.LENGTH_LONG).show()

        }
    }

    private fun getUserDetails() {
    }

    fun fetchApi() {
        loading.value = true
        viewModelScope.launch {
            val response = api.fetchApod("TWfxRVcoV13BrOchR0hZf2osOjdk5WfnQSvJvUiM")
            Log.d("Api", response.body().toString())
            apod.value = response.body()
            loading.value = false
        }
    }
}