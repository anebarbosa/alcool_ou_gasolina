package com.example.alcoolougasolina.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolina.R
import com.example.alcoolougasolina.data.model.FuelCategory
import com.example.alcoolougasolina.data.model.GasStation
import com.example.alcoolougasolina.data.repository.GasStationRepository
import com.example.alcoolougasolina.data.repository.PreferencesRepository
import com.example.alcoolougasolina.ui.navigation.Routes
import com.example.alcoolougasolina.utils.openInMap
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun formatDate(isoDate: String): String {
  return try {
    val dateTime = LocalDateTime.parse(isoDate)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    dateTime.format(formatter)
  } catch (e: Exception) {
    isoDate
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GasStationDetailScreen(navController: NavHostController, id: String?) {
  val context = LocalContext.current
  val gasStationRepository = remember { GasStationRepository(context) }
  val preferencesRepository = remember { PreferencesRepository(context) }

  var gasStation by remember { mutableStateOf<GasStation?>(null) }

  LaunchedEffect(id) {
    if (id == null) {
      navController.popBackStack()
      return@LaunchedEffect
    }
    try {
      gasStation = gasStationRepository.read(id)
    } catch (e: Exception) {
      Toast.makeText(context, R.string.gas_station_not_found, Toast.LENGTH_SHORT).show()
      navController.popBackStack()
    }
  }

  val currentStation = gasStation ?: run {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
      CircularProgressIndicator()
    }
    return
  }

  val gasPrice = currentStation.fuelTypes.find { it.type == FuelCategory.GASOLINE }?.price ?: 0.0
  val ethanolPrice = currentStation.fuelTypes.find { it.type == FuelCategory.ETHANOL }?.price ?: 0.0

  val is75Percent = preferencesRepository.read().carEfficiencyIs75
  val betterFuel = currentStation.whichFuelIsBetterOption(is75Percent)
  val comparisonResult = if (betterFuel.type == FuelCategory.GASOLINE) {
    stringResource(R.string.gasoline_better)
  } else {
    stringResource(R.string.ethanol_better)
  }

  val currency = stringResource(R.string.currency_symbol) // Pega R$ ou $

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(currentStation.name ?: stringResource(R.string.list_gas_station_without_name)) },
        navigationIcon = { // ADICIONADO AQUI
          IconButton(onClick = { navController.popBackStack() }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
      )
    }
  ) { padding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(padding)
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
      ) {
        Column(modifier = Modifier.padding(16.dp)) {
          Text(
            text = stringResource(R.string.result),
            style = MaterialTheme.typography.titleMedium
          )
          HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.gas_price_label))
            Text("$currency %.2f".format(gasPrice), fontWeight = FontWeight.Bold) // APLICADO CURRENCY
          }
          Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.ethanol_price_label))
            Text("$currency %.2f".format(ethanolPrice), fontWeight = FontWeight.Bold) // APLICADO CURRENCY
          }

          Spacer(modifier = Modifier.height(16.dp))

          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(MaterialTheme.colorScheme.secondaryContainer, MaterialTheme.shapes.small)
              .padding(12.dp)
          ) {
            Text(
              text = comparisonResult, // O texto já vem traduzido da lógica acima
              modifier = Modifier.align(Alignment.Center),
              style = MaterialTheme.typography.bodyLarge,
              color = MaterialTheme.colorScheme.onSecondaryContainer,
              fontWeight = FontWeight.Bold
            )
          }
        }
      }

      Text(
        text = "${stringResource(R.string.date_label)} ${formatDate(currentStation.createdAt)}",
        style = MaterialTheme.typography.bodySmall
      )

      Spacer(modifier = Modifier.weight(1f))

      Button(
        onClick = {
          openInMap(context, currentStation.coordinates.latitude, currentStation.coordinates.longitude)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.secondary,
          contentColor = MaterialTheme.colorScheme.onSecondary // Cor da fonte
        )
      ) {
        Icon(Icons.Default.LocationOn, contentDescription = null)
        Spacer(Modifier.width(8.dp))
        Text(stringResource(R.string.view_on_map))
      }

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
          onClick = { navController.navigate(Routes.formWithId(currentStation.id)) },
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary // Cor da fonte
          )
        ) {
          Icon(Icons.Default.Edit, contentDescription = null)
          Spacer(Modifier.width(8.dp))
          Text(stringResource(R.string.edit_gas_station_button))
        }

        Button(
          onClick = {
            gasStationRepository.delete(currentStation.id)
            navController.popBackStack()
          },
          modifier = Modifier.weight(1f),
          colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error,
            contentColor = MaterialTheme.colorScheme.onError
          )
        ) {
          Icon(Icons.Default.Delete, contentDescription = null)
          Spacer(Modifier.width(8.dp))
          Text(stringResource(R.string.delete_gas_station_button))
        }
      }
    }
  }
}