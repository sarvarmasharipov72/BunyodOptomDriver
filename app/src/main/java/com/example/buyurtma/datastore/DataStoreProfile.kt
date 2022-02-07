package com.example.buyurtma.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.buyurtma.Constant
import kotlinx.coroutines.flow.first

class DataStoreProfile(private val context: Context) {

//    companion object {
//        private val dataStoreKey = stringPreferencesKey(Constant.TOKEN_KEY)
//        private val Context.token: DataStore<Preferences> by preferencesDataStore(name = "token")
//    }
//
//    suspend fun getToken(): String {
//        val preferences = context.token.data.first()
//        return preferences[dataStoreKey] ?: ""
//    }
//
//    suspend fun saveToken(token: String?) {
//
//        context.token.edit {
//            it[dataStoreKey] = token ?: ""
//        }
//    }
}
