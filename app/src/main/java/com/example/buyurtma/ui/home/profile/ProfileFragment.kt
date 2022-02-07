package com.example.buyurtma.ui.home.profile

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.buyurtma.Constant
import com.example.buyurtma.R
import com.example.buyurtma.Repository
import com.example.buyurtma.databinding.FragmentProfileBinding
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.home.profile.model.Profile
import com.example.buyurtma.ui.home.profile.model.ProfileData
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    companion object {
        private val nameKey = stringPreferencesKey(Constant.PROFILE_NAME_KEY)
        private val phoneKey = stringPreferencesKey(Constant.PROFILE_PHONE_KEY)
        private val Context.profileStore: DataStore<Preferences> by preferencesDataStore(name = "profile")

    }

    private var binding: FragmentProfileBinding? = null
    private var loginViewModel: LoginViewModel? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.bind(
            inflater.inflate(
                R.layout.fragment_profile,
                container,
                false
            )
        )

        val viewModelFactory = LoginViewModelFactory(Repository())
        loginViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(LoginViewModel::class.java)



        lifecycleScope.launch {
            var s: Profile? = getToken()
            if (s != null && s.data.name != "") {
//                navHostFragment.navigate(R.id.homeFragment)
                binding?.profileName?.text = s.data.name
                binding?.profilePhoneNumber?.text = "+${s.data.phone}"
            }
            postProfileData(activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
        }

        var token = homeViewModel.token.value.toString()
        loginViewModel?.getOrder("Bearer ${token}")
        loginViewModel?.profileData?.observe(requireActivity(), {
            if (it.isSuccessful) {
                binding?.profileName?.text = it.body()?.data?.name
                binding?.profilePhoneNumber?.text = "+${it.body()?.data?.phone}"
                lifecycleScope.launch {
                    saveProfile(it.body()?.data?.name, it.body()?.data?.phone)
                }
            }
        })

        return binding?.root
    }

    private suspend fun getToken(): Profile{
        val preferences = requireActivity().profileStore.data.first()
        return Profile(ProfileData(preferences[nameKey] ?: "", preferences[phoneKey] ?: ""))
    }

    private suspend fun saveProfile(name: String?, phone: String?) {
        requireActivity().profileStore.edit {
            it[nameKey] = name ?: ""
            it[phoneKey] = phone ?: ""
        }
    }

    private fun postProfileData(connectivityManager: ConnectivityManager) {
        if (connectivityManager.activeNetworkInfo?.isConnected == true) {
            loginViewModel?.getProfile("Bearer ${homeViewModel.token.value.toString()}")
        }
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
        loginViewModel = null
    }
}