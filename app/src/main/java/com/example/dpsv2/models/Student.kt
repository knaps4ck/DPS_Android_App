package com.example.dpsv2.models

import com.google.gson.annotations.SerializedName

data class Student(
    @SerializedName("suid")
    var suid : Float,
    @SerializedName("name")
    var name: String,
    @SerializedName("address")
    var address: String,
    @SerializedName("contact_number")
    var contact_number: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("emergency_number")
    var emergency_number: String,

)