package com.example.alcoolougasolina.data.model

import java.io.Serializable

data class Coordinates(
    val latitude: Double?,
    val longitude: Double?
) : Serializable {

    fun isValid(): Boolean {
        val lat = latitude ?: return false
        val lon = longitude ?: return false
        return lat in -90.0..90.0 && lon in -180.0..180.0
    }

    fun toFormattedString(): String {
        return if (isValid()) {
            "Lat: ${"%.4f".format(latitude)}, Lon: ${"%.4f".format(longitude)}"
        } else {
            "Localização não disponível"
        }
    }
}