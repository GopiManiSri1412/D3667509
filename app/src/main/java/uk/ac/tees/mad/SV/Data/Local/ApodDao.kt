package uk.ac.tees.mad.SV.Data.Local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import uk.ac.tees.mad.SV.Data.Remote.Apod

@Dao
interface ApodDao {

    @Query("SELECT * FROM apod")
    suspend fun getAll(): List<Apod>

    @Insert
    suspend fun insert(apod: Apod)

    @Query("SELECT * FROM apod WHERE title = :title")
    suspend fun getApodByTitle(title: String): Apod?

    @Transaction
    suspend fun insertIfNotExists(apod: Apod) {
        val existingApod = getApodByTitle(apod.title)
        if (existingApod == null) {
            insert(apod)
        }
    }

}