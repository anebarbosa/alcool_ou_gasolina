package com.example.alcoolougasolina.data.repository

import android.content.Context
import com.example.alcoolougasolina.data.KeyValueRepository
import com.example.alcoolougasolina.data.model.UserPreferences

class PreferencesRepository(private val context: Context) :
  KeyValueRepository<UserPreferences> {

  override val key: String = "UserPreferencesRepository"

  override fun read(): UserPreferences {
    return try {
      readHelper(context, UserPreferences::class.java)
        ?: UserPreferences()
    } catch (e: Exception) {
      UserPreferences()
    }
  }

  override fun save(objectModel: UserPreferences) {
    saveHelper(context, objectModel, UserPreferences::class.java)
  }
}