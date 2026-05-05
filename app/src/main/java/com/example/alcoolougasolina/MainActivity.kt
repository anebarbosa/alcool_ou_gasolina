package com.example.alcoolougasolina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.alcoolougasolina.ui.navigation.Routes
import com.example.alcoolougasolina.ui.screens.AlcoholOrGasolineScreen
import com.example.alcoolougasolina.ui.screens.GasStationDetailScreen
import com.example.alcoolougasolina.ui.screens.GasStationListScreen
import com.example.alcoolougasolina.ui.theme.AlcoolOuGasolina
import com.example.alcoolougasolina.util.requestLocationPermission

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()

    // Solicita permissão de localização para o mapa/coordenadas
    requestLocationPermission(this)

    setContent {
      AlcoolOuGasolina {
        val navController: NavHostController = rememberNavController()

        NavHost(
          navController = navController,
          startDestination = Routes.GasStationList.route
        ) {
          // Tela Principal: Listagem de Postos
          composable(Routes.GasStationList.route) {
            GasStationListScreen(navController)
          }

          // Tela de Formulário (Criação - Sem ID)
          composable(Routes.GasStationForm.route) {
            AlcoholOrGasolineScreen(navController, null)
          }

          // Tela de Formulário (Edição - Com ID)
          composable(
            route = "${Routes.GasStationForm.route}/{${Routes.ARG_STATION_ID}}",
            arguments = listOf(
              navArgument(Routes.ARG_STATION_ID) { type = NavType.StringType }
            )
          ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Routes.ARG_STATION_ID)
            AlcoholOrGasolineScreen(navController, id)
          }

          // Tela de Detalhes/Informações do Posto
          composable(
            route = "${Routes.GasStationInfo.route}/{${Routes.ARG_STATION_ID}}",
            arguments = listOf(
              navArgument(Routes.ARG_STATION_ID) { type = NavType.StringType }
            )
          ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(Routes.ARG_STATION_ID)
            GasStationDetailScreen(navController, id)
          }
        }
      }
    }
  }
}