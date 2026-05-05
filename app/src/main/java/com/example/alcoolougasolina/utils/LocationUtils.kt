package com.example.alcoolougasolina.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

/**
 * Solicita as permissões de localização (Fina e Aproximada).
 * O código 1001 é usado para identificar esta solicitação específica no onRequestPermissionsResult.
 */
fun requestLocationPermission(activity: Activity) {
    if (!hasLocationPermission(activity)) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            1001
        )
    }
}

/**
 * Verifica se as permissões necessárias foram concedidas.
 */
fun hasLocationPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

/**
 * Obtém a última localização conhecida do dispositivo.
 * @param onSuccess Callback chamado quando a localização é encontrada.
 * @param onFailure Callback chamado quando a localização é nula ou não há permissão.
 */
fun getLastLocation(
    context: Context,
    onSuccess: (lat: Double, lon: Double) -> Unit,
    onFailure: () -> Unit = {}
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    if (hasLocationPermission(context)) {
        try {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    if (location != null) {
                        onSuccess(location.latitude, location.longitude)
                    } else {
                        onFailure()
                    }
                }
                .addOnFailureListener {
                    onFailure()
                }
        } catch (e: SecurityException) {
            onFailure()
        }
    } else {
        onFailure()
    }
}