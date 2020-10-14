package com.sample.demo.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "user")
@Parcelize
data class User(
    @PrimaryKey
    val id: Int,
    val name: String,
    val email: String,
    val gender: String,
    val status: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    val uploadedStatus: Int = 1 // 1 = uploaded and 0 = pending
): Parcelable