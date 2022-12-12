package com.example.dpsv2.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("students")
    @Expose
    var students : List<Student>
){
    fun getStudent(): Student { return students[0] }
}
