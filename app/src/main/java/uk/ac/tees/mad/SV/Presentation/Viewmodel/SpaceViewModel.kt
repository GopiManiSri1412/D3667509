package uk.ac.tees.mad.SV.Presentation.Viewmodel

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.api.Authentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(
    private val authentication: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    val loading = mutableStateOf(false)
    val signed = mutableStateOf(false)
    init {
        if(authentication.currentUser != null){
            signed.value = true
            getUserDetails()
        }
    }

    fun signUp(context: Context, name: String, email: String, password:String){
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
                Toast.makeText(context,"Sign up successful",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                loading.value = false
                Toast.makeText(context,"Sign up failed",Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            loading.value = false
            Toast.makeText(context,"Sign up failed",Toast.LENGTH_LONG).show()
        }
    }

    fun logIn(context: Context, email: String, password: String){
        loading.value = true
        authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loading.value = false
            signed.value = true
            getUserDetails()
            Toast.makeText(context,"Log in successful",Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            loading.value = false
            Toast.makeText(context,"Log in failed",Toast.LENGTH_LONG).show()

        }
    }

    private fun getUserDetails() {
    }
}