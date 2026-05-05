package com.example.alcoolougasolina.data

import android.content.Context
import com.example.alcoolougasolina.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

interface KeyValueRepository<Model> {
  val key: String

  companion object {
    val gson = Gson()
  }

  fun read(): Model
  fun save(objectModel: Model)

  fun saveHelper(context: Context, value: Model, type: Class<Model>) {
    try {
      val json = gson.toJson(value)
      val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_preferences_name),
        Context.MODE_PRIVATE
      )
      sharedPreferences.edit().putString(key, json).apply()
    } catch (e: Exception) {
    }
  }

  fun readHelper(context: Context, type: Class<Model>): Model? {
    val sharedPreferences = context.getSharedPreferences(
      context.getString(R.string.app_preferences_name),
      Context.MODE_PRIVATE
    )

    val objectJson = sharedPreferences.getString(key, null) ?: return null

    return try {
      gson.fromJson(objectJson, type)
    } catch (e: JsonSyntaxException) {
      null
    }
  }
}