package com.example.alcoolougasolina.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.alcoolougasolina.R
import com.example.alcoolougasolina.data.model.*
import com.example.alcoolougasolina.data.repository.GasStationRepository
import com.example.alcoolougasolina.data.repository.PreferencesRepository
import com.example.alcoolougasolina.util.getLastLocation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlcoholOrGasolineScreen(navController: NavHostController, id: String?) {
    val context = LocalContext.current

    val gasStationRepository = remember { GasStationRepository(context) }
    val preferencesRepository = remember { PreferencesRepository(context) }

    val existingStation = remember(id) {
        id?.let { gasStationRepository.read(it) }
    }

    var name by remember { mutableStateOf(existingStation?.name ?: "") }
    var ethanolPrice by remember {
        mutableStateOf(existingStation?.fuelTypes?.find { it.type == FuelCategory.ETHANOL }?.price?.toString() ?: "")
    }
    var gasPrice by remember {
        mutableStateOf(existingStation?.fuelTypes?.find { it.type == FuelCategory.GASOLINE }?.price?.toString() ?: "")
    }

    var is75Percent by remember { mutableStateOf(preferencesRepository.read().carEfficiencyIs75) }
    var resultMessage by remember { mutableStateOf("") }

    val defaultStationName = stringResource(R.string.list_gas_station_without_name)
    val invalidValuesError = stringResource(R.string.invalid_values_error)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = MaterialTheme.colorScheme.secondary,
        focusedLabelColor = MaterialTheme.colorScheme.secondary,
        cursorColor = MaterialTheme.colorScheme.secondary,
        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
    )

    fun handleSave(lat: Double?, lon: Double?) {
        val fuelList = listOf(
            FuelType(type = FuelCategory.GASOLINE, price = gasPrice.toDoubleOrNull() ?: 0.0),
            FuelType(type = FuelCategory.ETHANOL, price = ethanolPrice.toDoubleOrNull() ?: 0.0)
        )

        val station = GasStation(
            id = id ?: java.util.UUID.randomUUID().toString(),
            name = name.ifBlank { defaultStationName },
            fuelTypes = fuelList,
            coordinates = Coordinates(
                latitude = lat ?: existingStation?.coordinates?.latitude,
                longitude = lon ?: existingStation?.coordinates?.longitude
            )
        )

        if (id == null) {
            gasStationRepository.save(station)
        } else {
            gasStationRepository.edit(id, station)
        }

        navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (id == null) stringResource(R.string.insert_gas_station) else stringResource(R.string.edit_gas_station_button))
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.alcool_ou_gasolina_logo),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp).padding(bottom = 8.dp)
                )

                OutlinedTextField(
                    value = ethanolPrice,
                    onValueChange = { ethanolPrice = it },
                    label = { Text(stringResource(R.string.ethanol_price_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = textFieldColors
                )

                OutlinedTextField(
                    value = gasPrice,
                    onValueChange = { gasPrice = it },
                    label = { Text(stringResource(R.string.gas_price_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    colors = textFieldColors
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.station_name_label)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = textFieldColors
                )

                if (resultMessage.isNotEmpty()) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = resultMessage,
                            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.efficiency_percentage_label),
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp
                    )
                    Switch(
                        checked = is75Percent,
                        onCheckedChange = { checked ->
                            is75Percent = checked
                            preferencesRepository.save(UserPreferences(checked))
                        },
                        thumbContent = if (is75Percent) {
                            { Icon(Icons.Filled.Check, null, Modifier.size(SwitchDefaults.IconSize)) }
                        } else null
                    )
                }

                Button(
                    onClick = {
                        val gPrice = gasPrice.toDoubleOrNull() ?: 0.0
                        val ePrice = ethanolPrice.toDoubleOrNull() ?: 0.0
                        if (gPrice > 0 && ePrice > 0) {
                            val ratio = ePrice / gPrice
                            val threshold = if (is75Percent) 0.75 else 0.70
                            resultMessage = if (ratio <= threshold) {
                                context.getString(R.string.ethanol_better)
                            } else {
                                context.getString(R.string.gasoline_better)
                            }
                        } else {
                            resultMessage = invalidValuesError
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.calculate_only_button),
                        fontWeight = FontWeight.Bold
                    )
                }

                Button(
                    onClick = {
                        getLastLocation(
                            context = context,
                            onSuccess = { lat, lon -> handleSave(lat, lon) },
                            onFailure = { handleSave(null, null) }
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.tertiary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.save_station_button),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}