package uk.ac.tees.mad.SV.Domain.depenedencies

import android.content.Context
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uk.ac.tees.mad.SV.Data.Local.ApodDao
import uk.ac.tees.mad.SV.Data.Local.ApodDatabase
import uk.ac.tees.mad.SV.Data.Remote.NasaApi
import javax.inject.Singleton

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

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ApodDatabase {
        return Room.databaseBuilder(
            appContext,
            ApodDatabase::class.java,
            "apod_database"
        ).build()
    }

    @Provides
    fun provideApodDao(database: ApodDatabase): ApodDao {
        return database.apodDao()
    }
}