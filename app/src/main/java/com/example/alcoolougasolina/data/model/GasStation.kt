package com.example.alcoolougasolina.data.model

import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

data class GasStation(
  val id: String = UUID.randomUUID().toString(),
  val name: String?,
  val fuelTypes: List<FuelType>,
  val coordinates: Coordinates = Coordinates(null, null),
  val createdAt: String = getCurrentTime(),
) : Serializable {

  init {
    val categories = fuelTypes.map { it.type }
    require(categories.contains(FuelCategory.ETHANOL)) { "Preço do Álcool é obrigatório" }
    require(categories.contains(FuelCategory.GASOLINE)) { "Preço da Gasolina é obrigatório" }
  }

  fun whichFuelIsBetterOption(carHas75Efficiency: Boolean): FuelType {
    val proportionToCompare = if (carHas75Efficiency) 0.75 else 0.70

    val ethanol = fuelTypes.find { it.type == FuelCategory.ETHANOL }!!
    val gas = fuelTypes.find { it.type == FuelCategory.GASOLINE }!!

    if (gas.price <= 0) return ethanol

    val priceProportion = ethanol.price / gas.price

    return if (priceProportion > proportionToCompare) gas else ethanol
  }
}

fun getCurrentTime(): String {
  return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}