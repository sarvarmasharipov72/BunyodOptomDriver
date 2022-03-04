package com.example.buyurtma.ui.login

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.buyurtma.R
import com.example.buyurtma.Repository
import com.example.buyurtma.databinding.FragmentLoginOneBinding
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory
import com.example.buyurtma.ui.login.model.Model
import com.google.android.material.textfield.TextInputEditText
import java.lang.StringBuilder

class LoginFragmentOne : Fragment() {

    private lateinit var binding: FragmentLoginOneBinding
    private lateinit var viewModel: LoginViewModel
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var alertDialog: AlertDialog
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginOneBinding.bind(
            inflater.inflate(
                R.layout.fragment_login_one,
                container,
                false
            )
        )
        connectivityManager =
            activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        viewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(Repository())
        ).get(LoginViewModel::class.java)

        initAlert()

        var backSpace: Boolean = false
        var i = 0
        binding.phoneNumber.addTextChangedListener {
            val j = it?.length
            if (j != null) {
                if (j >= i) {
                    when (it.length) {
                        4 -> {
                            binding.phoneNumber.text?.append(' ')
                        }
                        7 -> {
                            binding.phoneNumber.text?.append(' ')
                        }
                        11 -> {
                            binding.phoneNumber.text?.append(' ')
                        }
                        14 -> {
                            binding.phoneNumber.text?.append(' ')
                        }
                    }
                }
            }
            i = it?.length!!
        }
        binding.phoneNumber.setOnTouchListener { v, event ->
            if (binding.phoneNumber.text!!.isBlank()) {
                binding.phoneNumber.text?.append("+998 ")
            }
            return@setOnTouchListener false
        }
        binding.phoneNumber.setOnKeyListener { v, keyCode, event ->
            backSpace = keyCode == 67
            if (keyCode == 66) {
                validatePhoneNumber(binding.phoneNumber)
            }
            false
        }
        binding.password.setOnKeyListener { v, keyCode, event ->

            if (keyCode == 66) {
                validatePasword(binding.password)
            }
            false
        }


        binding.btnKirishOne.setOnClickListener {
            if (validationInputData() && connectivityManager.activeNetworkInfo?.isConnected == true) {

                viewModel.postKuryer(
                    Model(
                        deleteSpace(binding.phoneNumber.text.toString()),
                        binding.password.text.toString()
                    )
                )
                alertDialog.show()
            } else if (connectivityManager.activeNetworkInfo?.isConnected != true) {
                val toast = Toast.makeText(requireContext(), "Internet yo'q", Toast.LENGTH_LONG)
                toast.setMargin(0f, 100f)
                toast.show()
            }
        }

        viewModel.token.observe(requireActivity(), {
            if (it.isSuccessful) {
                alertDialog.cancel()
                homeViewModel.setToken(it.body()?.token.toString())
                findNavController().navigate(R.id.action_loginFragmentOne_to_homeFragment)
            } else {
                alertDialog.cancel()
                binding.erorMessage.text = "Telefon raqam yoki parol xato"
                binding.erorMessage.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

    private fun initAlert() {
        val inflater = requireActivity().layoutInflater.inflate(R.layout.loading_item, null)
        alertDialog = AlertDialog.Builder(requireContext())
            .setView(inflater)
            .create()
    }

    private fun validationInputData(): Boolean {
        return validatePhoneNumber(binding.phoneNumber) && validatePasword(binding.password)
    }

    private fun validatePhoneNumber(phoneNumber: TextInputEditText): Boolean {
        val phoneNumberText = deleteSpace(phoneNumber.text.toString())
        if (phoneNumberText.isEmpty()) {
            phoneNumber.error = "Telefon raqam bo'sh bo'lmasligi kerak"
            return false
        }
        if (phoneNumberText.length != 12) {
            phoneNumber.error = "+998 12 345 67 89 shu tartibda kiriting"
            return false
        }
        if (!phoneNumberText.substring(0, 3).equals("998")) {
            phoneNumber.error = "+998 bilan boshlanishi kerak"
            return false
        }

        return true
    }

    private fun validatePasword(password: TextInputEditText): Boolean {
        if (password.text?.length == 0) {
            password.error = "Parol bo'sh bo'lmasligi kerak"
            return false
        }
        return true
    }

    private fun deleteSpace(phoneNumberString: String): String {
        val s = StringBuilder()
        phoneNumberString.forEach {
            if (it in '0'..'9')
                s.append(it)
        }
        return s.toString()
    }


}