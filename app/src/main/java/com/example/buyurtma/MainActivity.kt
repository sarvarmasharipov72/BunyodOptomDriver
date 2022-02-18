package com.example.buyurtma

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        private val dataStoreKey = stringPreferencesKey(Constant.TOKEN_KEY)
        private val dataStoreData = intPreferencesKey(Constant.DATE_KEY)
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    }

    private val homeViewModel: HomeViewModel by viewModels()
    private var loginViewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(Repository())
        )[LoginViewModel::class.java]
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        lifecycleScope.launch {
            var s: String? = null
            if (homeViewModel.token.value == null) {
                val n = getDate()
                s = if (n != 0) {
                    ""
                } else {
                    getToken()
                }
            }
            if (s != null && s != "") {
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
        loginViewModel?.orders?.observe(this, {
            if (it.isSuccessful) {
                homeViewModel.orders.value = it.body()
            }
        })

    }

    private suspend fun getToken(): String? {
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

    private suspend fun saveToken(token: String?) {
        getOrder(token ?: "token not found")
        val calendar = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        dataStore.edit {
            it[dataStoreKey] = token ?: ""
            it[dataStoreData] = calendar

        }
    }
    private suspend fun getDate(): Int {
        val day = dataStore.data.first()[dataStoreData]
        val calendar = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        return calendar - (day ?: calendar + 1)
    }
    private fun getOrder(token: String) {
//        loginViewModel?.getOrder("Bearer $token")
    }
}