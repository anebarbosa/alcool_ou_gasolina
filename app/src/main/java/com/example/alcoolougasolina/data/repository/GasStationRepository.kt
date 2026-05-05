package com.example.alcoolougasolina.data.repository

import android.content.Context
import com.example.alcoolougasolina.data.Repository
import com.example.alcoolougasolina.data.model.GasStation

class GasStationRepository(private val context: Context) : Repository<GasStation> {

  override val key: String = "GasStationRepository"

  override fun readAll(): List<GasStation> {
    return this.readHelper(context, GasStation::class.java)
      .sortedByDescending { it.createdAt }
  }

  override fun read(id: String): GasStation {
    return readAll().find { it.id == id }
      ?: throw NoSuchElementException("Posto com ID $id não encontrado.")
  }

  override fun delete(id: String) {
    val updatedList = readAll().filter { it.id != id }
    saveHelper(context, updatedList, GasStation::class.java)
  }

  override fun edit(id: String, objectModel: GasStation) {
    val allStations = readAll().toMutableList()
    val index = allStations.indexOfFirst { it.id == id }

    if (index != -1) {
      allStations[index] = objectModel.copy(id = id, createdAt = allStations[index].createdAt)
      saveHelper(context, allStations, GasStation::class.java)
    }
  }

  override fun save(objectModel: GasStation) {
    val allStations = readAll().toMutableList()

    val existingIndex = allStations.indexOfFirst { it.id == objectModel.id }
    if (existingIndex != -1) {
      allStations[existingIndex] = objectModel
    } else {
      allStations.add(objectModel)
    }

    saveHelper(context, allStations, GasStation::class.java)
  }
}