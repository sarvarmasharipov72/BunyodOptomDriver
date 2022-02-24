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
import com.example.buyurtma.ui.login.model.Login
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
        connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        viewModel = ViewModelProvider(this, LoginViewModelFactory(Repository())).get(LoginViewModel::class.java)

        initAlert()

        var backSpace: Boolean = false
        var i = 0
        binding.phoneNumber.addTextChangedListener {
            var j = it?.length
            if (j != null) {
                if (j >= i) {
                    if (it?.length == 4) {
                        binding.phoneNumber.text?.append(' ')
                    } else if (it?.length == 7) {
                        binding.phoneNumber.text?.append(' ')
                    } else if (it?.length == 11) {
                        binding.phoneNumber.text?.append(' ')
                    } else if (it?.length == 14) {
                        binding.phoneNumber.text?.append(' ')
                    }
                }
            }
            i = it?.length!!
        }
        binding.phoneNumber.setOnKeyListener { v, keyCode, event ->
//            var editable = binding.password.text
//            binding.phoneNumber.text = when (editable?.length) {
//                +998 91 277 12 23
//                4 -> binding.phoneNumber.text?.insert(5, " ")
//                7 -> binding.phoneNumber.text?.insert(8, " ")
//                12 -> binding.phoneNumber.text?.insert(13, " ")
//                else -> Editable.Factory.getInstance().newEditable(editable).append("")
//            }
            backSpace = keyCode == 67
            if (keyCode == 66){
                validatePhoneNumber(binding.phoneNumber)
            }
            false
        }
        binding.password.setOnKeyListener { v, keyCode, event ->

            if (keyCode == 66){
                validatePasword(binding.password)
            }
            false
        }


        binding.btnKirishOne.setOnClickListener {
            if (validationInputData() && connectivityManager.activeNetworkInfo?.isConnected == true) {
                val action = LoginFragmentOneDirections.actionLoginFragmentOneToLoginFragmentTwo(
                    Login(
                        deleteSpace(binding.phoneNumber.text.toString()),
                        binding.password.text.toString()
                    )
                )
                viewModel.postKuryer(
                    Model(
                    deleteSpace(binding.phoneNumber.text.toString()),
                    binding.password.text.toString()
                )
                )
                alertDialog.show()
//                onDetach()
//                findNavController().navigate(action)
            }
            else if (connectivityManager.activeNetworkInfo?.isConnected != true) {
                var toast = Toast.makeText(requireContext(),"Internet yo'q",Toast.LENGTH_LONG)
                toast.setMargin(0f, 100f)
                toast.show()
            }
        }

        viewModel.token.observe(requireActivity(), {
            if (it.isSuccessful) {
                alertDialog.cancel()
                homeViewModel.setToken(it.body()?.token.toString())
                findNavController().navigate(R.id.action_loginFragmentOne_to_homeFragment)
            }
            else {
                alertDialog.cancel()
               binding.erorMessage.text = "Telefon raqam yoki parol xato"
                binding.erorMessage.visibility = View.VISIBLE
            }
        })

        return binding.root
    }

    private fun initAlert() {
        val inflater = requireActivity().layoutInflater.inflate(R.layout.loading_item,null)
        alertDialog = AlertDialog.Builder(requireContext())
            .setView(inflater)
            .create()
    }

    fun validationInputData(): Boolean {
        return validatePhoneNumber(binding.phoneNumber) && validatePasword(binding.password)
    }

    fun validatePhoneNumber(phoneNumber: TextInputEditText): Boolean {
        val phoneNumberText = deleteSpace(phoneNumber.text.toString())
        if (phoneNumberText.length == 0) {
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

    fun validatePasword(password: TextInputEditText): Boolean {
        if (password.text?.length == 0) {
            password.error = "Parol bo'sh bo'lmasligi kerak"
            return false
        }
//        if (password.text?.length!! < 8) {
//            password.setError("Parol uzunligi 8 dan kam bo'lmasin+")
//            return false
//        }
        return true
    }
    fun deleteSpace(phoneNumberString: String): String {
        val s = StringBuilder()
        phoneNumberString.forEach {
            if (it in '0'..'9')
                s.append(it)
        }
        return s.toString()
    }


}