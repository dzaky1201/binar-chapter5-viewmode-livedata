package com.dzakyhdr.networkingsample02.data.register


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterRequest(
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("role")
    val role: String?
): Parcelable