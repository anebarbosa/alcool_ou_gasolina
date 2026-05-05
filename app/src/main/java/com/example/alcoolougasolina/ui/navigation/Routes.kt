package com.example.alcoolougasolina.ui.navigation

sealed class Routes(val route: String) {
  // Rotas básicas
  object GasStationList : Routes("gas_station_list")
  object GasStationForm : Routes("gas_station_form")
  object GasStationInfo : Routes("gas_station_info")

  companion object {
    // Nome do argumento que o NavHost vai procurar
    const val ARG_STATION_ID = "stationId"

    /**
     * Funções utilitárias para construir a URL de navegação com o ID.
     * Ex: "gas_station_info/123-abc"
     */
    fun formWithId(id: String) = "${GasStationForm.route}/$id"
    fun infoWithId(id: String) = "${GasStationInfo.route}/$id"
  }
}