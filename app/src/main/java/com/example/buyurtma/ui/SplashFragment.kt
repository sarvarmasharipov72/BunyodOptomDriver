package com.example.buyurtma.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buyurtma.Constant
import com.example.buyurtma.R
import com.example.buyurtma.ui.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*

class SplashFragment : Fragment() {
    private val homeViewModel: HomeViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        homeViewModel.check.observe(this, {
            if (it == 1) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)

            } else if (it == 2) {
                findNavController().navigate(R.id.action_splashFragment_to_loginFragmentOne)
            }
        })

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


}