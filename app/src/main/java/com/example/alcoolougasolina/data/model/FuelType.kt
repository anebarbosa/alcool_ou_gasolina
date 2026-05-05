package com.example.alcoolougasolina.data.model

import java.io.Serializable
import java.text.NumberFormat

enum class FuelCategory {
    ETHANOL, GASOLINE
}

data class FuelType(
    val type: FuelCategory,
    val price: Double
) : Serializable {

    fun getFormattedPrice(): String {
        val format = NumberFormat.getCurrencyInstance()
        return format.format(price)
    }
}