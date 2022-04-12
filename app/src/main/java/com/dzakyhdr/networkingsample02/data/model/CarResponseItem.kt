package com.dzakyhdr.networkingsample02.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class CarResponseItem(
    @SerializedName("category")
    val category: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("finish_rent_at")
    val finishRentAt: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("price")
    val price: Int?,
    @SerializedName("start_rent_at")
    val startRentAt: String?,
    @SerializedName("status")
    val status: Boolean?,
    @SerializedName("updatedAt")
    val updatedAt: String?
): Parcelable