package com.example.dpsv2.models

import com.example.dpsv2.utils.Constants
private val constants = Constants()

data class Rides(
    var rideID: String? = null,
    val suid: String? = null,
    val source: String? = null,
    val destination: String? = null,
    val driver: String? = null,
    val pickuptime: String? = null,
    val ridestatus: String? = constants.NEW_RIDE_STATUS
)