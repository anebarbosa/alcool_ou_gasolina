package com.example.alcoolougasolina.data.model

import org.junit.Assert.assertEquals
import org.junit.Test

class GasStationTest {

  @Test
  fun shouldReturnEthanolWhenProportionIsLower75AndTheCarProfileIs75() {
    val gas = FuelType(name = "gas", price = 6.5)
    val ethanol = FuelType(name = "ethanol", price = 4.81)
    val gasStation =
      GasStation(
        name = null,
        fuelTypes = listOf(gas, ethanol),
        coordinates = Coordinates(latitude = -3.7528539, longitude = -38.5578778),
      )
    assertEquals(ethanol, gasStation.whichFuelIsBetterOption(true))
  }

  @Test
  fun shouldReturnGasWhenProportionIsBigger75AndTheCarProfileIs75() {
    val gas = FuelType(name = "gas", price = 6.5)
    val ethanol = FuelType(name = "ethanol", price = 5.0)
    val gasStation =
      GasStation(
        name = null,
        fuelTypes = listOf(gas, ethanol),
        coordinates = Coordinates(latitude = -3.7528539, longitude = -38.5578778),
      )
    assertEquals(gas, gasStation.whichFuelIsBetterOption(true))
  }
}
