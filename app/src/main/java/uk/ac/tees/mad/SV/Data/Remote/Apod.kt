package uk.ac.tees.mad.SV.Data.Remote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "apod")
data class Apod(
    @PrimaryKey(autoGenerate = true) val id : Int = 0,
    val copyright: String?,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)