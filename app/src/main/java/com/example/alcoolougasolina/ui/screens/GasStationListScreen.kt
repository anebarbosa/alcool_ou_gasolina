package com.example.alcoolougasolina.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.alcoolougasolina.R
import com.example.alcoolougasolina.data.model.FuelCategory
import com.example.alcoolougasolina.data.repository.GasStationRepository
import com.example.alcoolougasolina.ui.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GasStationListScreen(navController: NavHostController) {
  val context = LocalContext.current
  val gasStationRepository = remember { GasStationRepository(context) }
  val gasStationsList = remember { gasStationRepository.readAll() }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(stringResource(id = R.string.list_gas_station_title)) },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer,
          titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
      )
    },
    floatingActionButton = {
      FloatingActionButton(
        onClick = { navController.navigate(Routes.GasStationForm.route) },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
      ) {
        Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.insert_gas_station))
      }
    }
  ) { innerPadding ->
    if (gasStationsList.isEmpty()) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding),
        contentAlignment = Alignment.Center
      ) {
        Text(
          text = stringResource(id = R.string.insert_gas_station),
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }
    } else {
      LazyColumn(
        modifier = Modifier
          .fillMaxSize()
          .padding(innerPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        items(gasStationsList) { station ->
          GasStationCard(
            name = station.name ?: stringResource(R.string.list_gas_station_without_name),
            gasPrice = station.fuelTypes.find { it.type == FuelCategory.GASOLINE }?.price ?: 0.0,
            ethanolPrice = station.fuelTypes.find { it.type == FuelCategory.ETHANOL }?.price ?: 0.0,
            onClick = {
              navController.navigate(Routes.infoWithId(station.id))
            }
          )
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GasStationCard(
  name: String,
  gasPrice: Double,
  ethanolPrice: Double,
  onClick: () -> Unit
) {
  Card(
    onClick = onClick,
    modifier = Modifier.fillMaxWidth(),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Text(
        text = name,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.weight(1f)
      )

      Column(horizontalAlignment = Alignment.End) {
        PriceRow(label = stringResource(R.string.gas_price_label).take(3) + ":", price = gasPrice)
        PriceRow(label = stringResource(R.string.ethanol_price_label).take(3) + ":", price = ethanolPrice)
      }
    }
  }
}

@Composable
fun PriceRow(label: String, price: Double) {
  val currency = stringResource(R.string.currency_symbol)

  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(
      text = label,
      style = MaterialTheme.typography.labelSmall,
      color = MaterialTheme.colorScheme.secondary
    )
    Spacer(modifier = Modifier.width(4.dp))
    Text(
      text = "$currency %.2f".format(price),
      style = MaterialTheme.typography.bodyMedium,
      fontWeight = FontWeight.Medium
    )
  }
}