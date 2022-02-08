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
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.home.profile.model.Profile
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        private val dataStoreKey = stringPreferencesKey(Constant.TOKEN_KEY)
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token")

    }

    val homeViewModel: HomeViewModel by viewModels()
    private var loginViewModel: LoginViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(Repository())).get(LoginViewModel::class.java)
        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment).navController
        lifecycleScope.launch {
            var s: String? = null
            if (homeViewModel.token.value == null) {
                s = getToken()
            }
            if (s != null && s != "") {
                homeViewModel.setToken(s)
                loginViewModel?.getOrder("Bearer $s")
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

        dataStore.edit {
            it[dataStoreKey] = token ?: ""

        }
    }
}