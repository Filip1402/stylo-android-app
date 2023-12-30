package com.airstrike.stylo.helpers

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SecurePreferencesManager(context : Context) {
    private val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val gson = Gson()

    fun saveObject(key: String, obj: Any) {
        val jsonString = gson.toJson(obj)
        saveData(key, jsonString)
    }

    fun getObject(key: String, clazz: Class<*>): Any? {
        val jsonString = getData(key)
        return if (jsonString != null) {
            gson.fromJson(jsonString, clazz)
        } else {
            null
        }
    }
    inline fun <reified T> getObjects(key: String, clazz: Class<T>): ArrayList<T>? {
        val jsonString = getData(key)
        return if (jsonString != null) {
            val type = object : TypeToken<ArrayList<T>>() {}.type
            gson.fromJson(jsonString, type)
        } else {
            null
        }
    }

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    // Function to retrieve data from EncryptedSharedPreferences
    fun getData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}