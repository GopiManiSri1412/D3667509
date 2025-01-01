package uk.ac.tees.mad.SV.Data.Local

import androidx.room.Database
import androidx.room.RoomDatabase
import uk.ac.tees.mad.SV.Data.Remote.Apod

@Database(entities = [Apod::class], version = 1, exportSchema = false)
abstract class ApodDatabase : RoomDatabase() {
    abstract fun apodDao(): ApodDao
}