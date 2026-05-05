package com.example.alcoolougasolina.data

import android.content.Context
import com.example.alcoolougasolina.R
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

interface Repository<Model> {
  val key: String

  companion object {
    val gson = Gson()
  }

  fun readAll(): List<Model>
  fun read(id: String): Model
  fun save(objectModel: Model)
  fun edit(id: String, objectModel: Model)
  fun delete(id: String)

  fun saveHelper(context: Context, allValues: List<Model>, type: Class<Model>) {
    try {
      val json = gson.toJson(allValues)
      val sharedPreferences = context.getSharedPreferences(
        context.getString(R.string.app_preferences_name),
        Context.MODE_PRIVATE
      )
      sharedPreferences.edit().putString(key, json).apply()
    } catch (e: Exception) {
    }
  }

  fun readHelper(context: Context, type: Class<Model>): List<Model> {
    val sharedPreferences = context.getSharedPreferences(
      context.getString(R.string.app_preferences_name),
      Context.MODE_PRIVATE
    )

    val objectJson = sharedPreferences.getString(key, null) ?: return emptyList()

    return try {
      val typeToken = TypeToken.getParameterized(List::class.java, type).type
      gson.fromJson(objectJson, typeToken) ?: emptyList()
    } catch (err: JsonSyntaxException) {
      emptyList()
    }
  }
}