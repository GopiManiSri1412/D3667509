package uk.ac.tees.mad.SV.Data.Remote

data class Apod(
    val copyright: String,
    val date: String,
    val explanation: String,
    val hdurl: String,
    val media_type: String,
    val service_version: String,
    val title: String,
    val url: String
)