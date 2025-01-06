package uk.ac.tees.mad.SV.Presentation.Viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.api.Authentication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uk.ac.tees.mad.SV.Data.Local.ApodDao
import uk.ac.tees.mad.SV.Data.Remote.Apod
import uk.ac.tees.mad.SV.Data.Remote.NasaApi
import uk.ac.tees.mad.SV.Data.Remote.UserData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SpaceViewModel @Inject constructor(
    private val authentication: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val api: NasaApi,
    private val apodDao: ApodDao,
    private val storage: FirebaseStorage,
) : ViewModel() {

    val apodOffline = MutableStateFlow<List<Apod>?>(null)
    val userData = mutableStateOf(UserData())
    val loading = mutableStateOf(false)
    val signed = mutableStateOf(false)
    val apod = mutableStateOf<Apod?>(null)

    init {
        if (authentication.currentUser != null) {
            signed.value = true
            getUserDetails()
            fetchApi()
            getFromApodDatabase()
        }
    }

    fun getFromApodDatabase() {
        viewModelScope.launch {
            apodOffline.value = apodDao.getAll()
        }
    }

    suspend fun getApodByTitle(title: String): Apod? {
        return apodDao.getApodByTitle(title)
    }

    fun insertIfNotExists(apod: Apod) {
        viewModelScope.launch {
            apodDao.insertIfNotExists(apod)
            getFromApodDatabase()
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
                fetchApi()
                getFromApodDatabase()
                Toast.makeText(context, "Sign up successful", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                loading.value = false
                Toast.makeText(context, "Sign up failed", Toast.LENGTH_LONG).show()
            }
        }.addOnFailureListener {
            loading.value = false
            Toast.makeText(context, "Sign up failed", Toast.LENGTH_LONG).show()
        }
    }

    fun logIn(context: Context, email: String, password: String) {
        loading.value = true
        authentication.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            loading.value = false
            signed.value = true
            getUserDetails()
            fetchApi()
            getFromApodDatabase()
            Toast.makeText(context, "Log in successful", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            loading.value = false
            Toast.makeText(context, "Log in failed", Toast.LENGTH_LONG).show()

        }
    }

    private fun getUserDetails() {
        firestore.collection("users").document(authentication.currentUser!!.email!!).get()
            .addOnSuccessListener {
                userData.value = it.toObject(UserData::class.java)!!
            }.addOnFailureListener {
                Log.d("Error", it.message.toString())
            }
    }

    fun fetchApi() {
        loading.value = true
        viewModelScope.launch {
            try {
                val date = Date()
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
                val response =
                    api.fetchApod("TWfxRVcoV13BrOchR0hZf2osOjdk5WfnQSvJvUiM", sdf)
                Log.d("Api", response.body().toString())
                apod.value = response.body()
                loading.value = false
            } catch (e: Exception) {
                Log.d("Error", e.message.toString())
            }
        }
    }

    fun updateProfilePicture(it: Uri) {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${authentication.currentUser!!.email!!}")
        val uploadTask = imageRef.putFile(it)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                firestore.collection("users").document(authentication.currentUser!!.email!!)
                    .update("profilePictureUrl", uri.toString()).addOnSuccessListener {
                        getUserDetails()
                    }
                    .addOnFailureListener {
                        Log.d("Error", it.message.toString())
                    }
            }.addOnFailureListener {
                Log.d("Error", it.message.toString())
            }
        }.addOnFailureListener {
            Log.d("Error", it.message.toString())

        }
    }

    fun findByTitle(title: String): Apod? {
        apodOffline.value?.forEach {
            if (it.title == title) {
                return it
            }
        }
        return null
    }

    fun deleteFromDb(apod: Apod) {
        viewModelScope.launch {
            apodDao.delete(apod)
            getFromApodDatabase()
        }
    }

    fun saveUserDetails(name: String) {
        firestore.collection("users").document(authentication.currentUser!!.email!!)
            .update("name", name).addOnSuccessListener {
                getUserDetails()
            }.addOnFailureListener {
                Log.d("Error", it.message.toString())
            }
    }

    fun signOut() {
        authentication.signOut()
        signed.value = false
    }
}