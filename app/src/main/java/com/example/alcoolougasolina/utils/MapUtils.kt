package com.example.alcoolougasolina.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

fun openInMap(context: Context, latitude: Double?, longitude: Double?, label: String? = "Posto") {
    if (latitude == null || longitude == null) {
        Toast.makeText(context, "Localização não disponível para este posto", Toast.LENGTH_SHORT).show()
        return
    }

    val uriString = "geo:$latitude,$longitude?q=$latitude,$longitude(${Uri.encode(label)})"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uriString))
    intent.setPackage("com.google.android.apps.maps")

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        // Fallback se o Google Maps não estiver instalado
        val webUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
        context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
    }
}