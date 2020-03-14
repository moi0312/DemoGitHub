package com.edlo.demogithub.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.edlo.demogithub.BuildConfig
import com.edlo.demogithub.DemoGitHubApplication
import com.edlo.demogithub.net.data.AccessToken

class SharedPreferencesHelper {
    enum class Keys {
        STR_USER, STR_ACESS_TOKEN,
    }

    companion object {
        val HELPER: SharedPreferencesHelper by lazy {
            SharedPreferencesHelper(DemoGitHubApplication.INSTANCE.applicationContext)
        }
    }

    private var instance: SharedPreferences

    private constructor(context: Context) {
        instance = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    fun reset(): SharedPreferencesHelper {
        instance.edit(commit = true) { clear() }
        return this
    }

    fun putToken(user: String, token: AccessToken): SharedPreferencesHelper {
        instance.edit(commit = true) {
            putString(Keys.STR_USER.name,  user)
//            putString(Keys.STR_ACESS_TOKEN.name, "${token.tokenType} ${token.accessToken}")
            putString(Keys.STR_ACESS_TOKEN.name, "token ${token.accessToken}")
        }
        return this
    }

    fun getToken(): String? {
        var value: String? = null
        try {
            value = instance.getString(Keys.STR_ACESS_TOKEN.name, null)
        } catch (e: ClassCastException) { }
        return value
    }


//    fun remove(key: Keys): SharedPreferencesHelper {
//        instance.edit(commit = true) { remove(key.name) }
//        return this
//    }
//
//    fun put(key: Keys, value: Set<String>?): SharedPreferencesHelper {
//        instance.edit(commit = true) { putStringSet(key.name,  value) }
//        return this
//    }
//    fun put(key: Keys, value: String?): SharedPreferencesHelper {
//        instance.edit(commit = true) { putString(key.name,  value) }
//        return this
//    }
//    fun put(key: Keys, value: Int): SharedPreferencesHelper {
//        instance.edit(commit = true) { putInt(key.name,  value) }
//        return this
//    }
//    fun put(key: Keys, value: Long): SharedPreferencesHelper {
//        instance.edit(commit = true) { putLong(key.name,  value) }
//        return this
//    }
//    fun put(key: Keys, value: Boolean): SharedPreferencesHelper {
//        instance.edit(commit = true) { putBoolean(key.name,  value) }
//        return this
//    }
//    fun put(key: Keys, value: Float): SharedPreferencesHelper {
//        instance.edit(commit = true) { putFloat(key.name,  value) }
//        return this
//    }
//
//    fun get(key: Keys, defValue: Set<String>? = null): Set<String>? {
//        var value = defValue
//        try {
//            value = instance.getStringSet(key.name, defValue)
//        } catch (e: ClassCastException) { }
//        return value
//    }
//    fun get(key: Keys, defValue: String? = null): String? {
//        var value = defValue
//        try {
//            value = instance.getString(key.name, defValue)
//        } catch (e: ClassCastException) { }
//        return value
//    }
//    fun get(key: Keys, defValue: Int = 0): Int {
//        var value = defValue
//        try {
//            value = instance.getInt(key.name, defValue)
//        } catch (e: ClassCastException) { }
//        return value
//    }
//    fun get(key: Keys, defValue: Long = 0): Long {
//        var value = defValue
//        try {
//            value = instance.getLong(key.name, defValue)
//        } catch (e: ClassCastException) { }
//        return value
//    }
//    fun get(key: Keys, defValue: Boolean = false): Boolean {
//        var value = defValue
//        try {
//            value = instance.getBoolean(key.name, defValue)
//        } catch (e: ClassCastException) { }
//        return value
//    }
//    fun get(key: Keys, defValue: Float = 0f): Float {
//        var value = defValue
//        try {
//            value = instance.getFloat(key.name, defValue)
//        } catch (e: ClassCastException) { }
//        return value
//    }
}