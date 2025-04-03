package com.cslori.core.data.auth

import android.content.SharedPreferences
import com.cslori.core.domain.AuthInfo
import com.cslori.core.domain.SessionStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.core.content.edit

class EncryptedSessionStorage(
    private val sharedPreferences: SharedPreferences,
) : SessionStorage {
    override suspend fun get(): AuthInfo? {
        return withContext(Dispatchers.IO) {
            val json = sharedPreferences.getString(KEY_AUTH_INFO, null) ?: return@withContext null
            Json.decodeFromString<AuthInfoSerializable>(json).toAuthInfo()
        }
    }

    override suspend fun set(info: AuthInfo?) {
        withContext(Dispatchers.IO) {
            if (info == null) {
                sharedPreferences.edit() { remove(KEY_AUTH_INFO) }
                return@withContext
            }
            val json = Json.encodeToString(info.toAuthInfoSerializable())
            sharedPreferences.edit() { putString(KEY_AUTH_INFO, json) }
        }
    }

    companion object {
        private const val KEY_AUTH_INFO = "auth_info"
    }
}