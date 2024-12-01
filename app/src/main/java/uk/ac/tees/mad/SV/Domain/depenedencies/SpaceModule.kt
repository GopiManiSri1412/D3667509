package uk.ac.tees.mad.SV.Domain.depenedencies

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.SV.Data.Remote.NasaApi

@Module
@InstallIn(SingletonComponent::class)
object SpaceModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideNasaApi(retrofit: Retrofit) = retrofit.create(NasaApi::class.java)
}