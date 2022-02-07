package com.example.buyurtma.ui.login

import android.app.AlertDialog
import android.content.Context
import android.graphics.ColorSpace
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.buyurtma.R
import com.example.buyurtma.databinding.FragmentLoginTwoBinding
import android.os.CountDownTimer
import android.os.Handler
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.buyurtma.Repository
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory
import com.example.buyurtma.ui.login.model.Login


class LoginFragmentTwo : Fragment() {

    private val args by navArgs<LoginFragmentTwoArgs>()
    private lateinit var timer: CountDownTimer
    private var binding: FragmentLoginTwoBinding? = null
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var dialog: AlertDialog
    private val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginTwoBinding.bind(inflater.inflate(R.layout.fragment_login_two, container, false))
        buildDialog()
        loginViewModel = ViewModelProvider(this, LoginViewModelFactory(Repository())).get(LoginViewModel::class.java)

        var phone = editNumber(args.loginParse.phone)

        homeViewModel.token.value = "KEY"
        binding?.btnKirishTwo?.setOnClickListener {

//            loginViewModel.postLogin(Login("",""))
            dialog.show()
            timer.cancel()

            Handler().postDelayed(Runnable {
            run {
                dialog.cancel()
            findNavController().navigate(R.id.action_loginFragmentTwo_to_homeFragment)
            }
            }, 5000)
        }

        binding?.btnBackLoginTwo?.setOnClickListener {
            timer.cancel()
            findNavController().popBackStack()
        }
        timer = object : CountDownTimer(59000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val spannableString = SpannableString("tasdiqlash kodi $phone ga sms orqali yuborildi. ushbu kodni 00:${millisUntilFinished/1000} vaqt ichida kiritishingiz kerak")

                val sColor = ForegroundColorSpan(resources.getColor(R.color.span_blue))
                val sColor1 = ForegroundColorSpan(resources.getColor(R.color.span_blue))
                val sColor2 = ForegroundColorSpan(resources.getColor(R.color.span_blue))

                spannableString.setSpan(sColor,0, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(sColor1,36, 72, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(sColor2, 78, spannableString.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

                spannableString.setSpan(StyleSpan(Typeface.BOLD), 73, 77, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                binding?.confirmCodeDetailes?.text = spannableString
            }

            override fun onFinish() {
                val spannableString = SpannableString("tasdiqlash kodi $phone ga sms orqali yuborildi. ushbu kodni 00:00 vaqt ichida kiritishingiz kerak")

                val sColor = ForegroundColorSpan(resources.getColor(R.color.span_blue))
                val sColor1 = ForegroundColorSpan(resources.getColor(R.color.span_blue))
                val sColor2 = ForegroundColorSpan(resources.getColor(R.color.span_blue))

                spannableString.setSpan(sColor,0, 16, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(sColor1,36, 72, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannableString.setSpan(sColor2, 78, spannableString.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)

                spannableString.setSpan(StyleSpan(Typeface.BOLD), 73, 77, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
                binding?.confirmCodeDetailes?.text = spannableString
            }
        }.start()
        return binding?.root
    }

    private fun buildDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater.inflate(R.layout.loading_item,null)
        builder.setView(inflater)
        dialog = builder.create()
    }

    private fun editNumber(phoneNumber: String): String {
        return "+${phoneNumber.substring(0, 3)} (${phoneNumber.substring(3,5)}) ${phoneNumber.substring(5,8)}-${phoneNumber.substring(8,10)}-${phoneNumber.substring(10,12)}"

    }

    override fun onDetach() {
        timer.cancel()
        binding = null
        super.onDetach()

    }

}