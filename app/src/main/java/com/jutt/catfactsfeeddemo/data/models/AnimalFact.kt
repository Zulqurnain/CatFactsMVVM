package com.jutt.catfactsfeeddemo.data.models


import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class AnimalFact(
    @PrimaryKey
    @SerializedName("_id")
    val id: String = "", // 58e008800aac31001185ed07
    @SerializedName("createdAt")
    val createdAt: String = "", // 2018-03-06T21:20:03.505Z
    @SerializedName("deleted")
    val deleted: Boolean = false, // false
    @SerializedName("source")
    val source: String = "", // user
    @Embedded @SerializedName("status")
    val status: Status = Status(),
    @SerializedName("text")
    val text: String = "", // Wikipedia has a recording of a cat meowing, because why not?
    @SerializedName("type")
    val type: String = "", // cat
    @SerializedName("updatedAt")
    val updatedAt: String = "", // 2020-08-23T20:20:01.611Z
    @SerializedName("used")
    val used: Boolean = false, // false
    @SerializedName("user")
    val user: String = "", // 58e007480aac31001185ecef
    @SerializedName("__v")
    val v: Int = 0 // 0
) : Parcelable {
    @Parcelize
    data class Status(
        @SerializedName("sentCount")
        val sentCount: Int = 0, // 1
        @SerializedName("verified")
        val verified: Boolean = false // true
    ) : Parcelable
}