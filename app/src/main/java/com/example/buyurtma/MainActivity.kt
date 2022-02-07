package com.example.buyurtma

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private val dataStoreKey = stringPreferencesKey(Constant.TOKEN_KEY)
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    }

    val homeViewModel: HomeViewModel by viewModels()
    val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        lifecycleScope.launch {
            var s: String? = null
            if (homeViewModel.token.value == null) {
                s = getToken()
                Log.d("Token", s.toString())
            }
            if (s != null && s != "") {
//                navHostFragment.navigate(R.id.homeFragment)
                homeViewModel.setToken(s)

            } else {
                navHostFragment.navigate(R.id.loginFragmentOne)
            }
        }
        homeViewModel.token.observe(this, {
            if (it != null) {
                lifecycleScope.launch {
                    Log.d("Token", it)
                    saveToken(it)
                }
            }
        })


    }

    private suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    private suspend fun saveToken(token: String?) {

        dataStore.edit {
            it[dataStoreKey] = token ?: ""

        }
    }
}