package com.example.buyurtma.ui.buyurtmalar

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyurtma.MainActivity
import androidx.lifecycle.ViewModelProvider
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory
import com.example.buyurtma.R
import com.example.buyurtma.Repository
import com.example.buyurtma.databinding.FragmentBuyurtmalarBinding
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import java.util.jar.Manifest

class BuyurtmalarFragment : Fragment() {

    private var binding: FragmentBuyurtmalarBinding? = null
    private val navArgs: BuyurtmalarFragmentArgs by navArgs()
    private var recyclerView: RecyclerView? = null
    private var adapter: BuyurtmaAdapter? = null
    private val homeViewModel: HomeViewModel by activityViewModels()
    private var loginViewModel: LoginViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBuyurtmalarBinding.bind(
            inflater.inflate(
                R.layout.fragment_buyurtmalar,
                container,
                false
            )
        )
        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(Repository())
        ).get(LoginViewModel::class.java)

        binding?.imageButtonBuyurtma?.setOnClickListener {
            findNavController().navigate(R.id.action_buyurtmalarFragment_to_homeSubFragment)
        }
        initRecyclerView()
        loginViewModel?.getData("Bearer ${homeViewModel.token.value.toString()}", navArgs.order.order._id)

        loginViewModel?.order?.observe(this, {
            if (it.isSuccessful) {
                adapter?.setData(it.body()?.data!!)
            }
        })


        binding?.btnCall?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CALL_PHONE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.CALL_PHONE),
                    100
                )
            } else {
                startCall()
            }
        }

            // Inflate the layout for this fragment
            return binding?.root
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 100) {
            startCall()
        } else {
            Toast.makeText(requireContext(), "Iltimos telefon qilish uchun ruxsat bering", Toast.LENGTH_LONG).show()
        }
    }

    private fun startCall() {
        val dailIntent = Intent(Intent.ACTION_CALL)
        dailIntent.data = Uri.parse("tel:+${loginViewModel?.order?.value?.body()?.data?.get(0)?.user?.phone}")
        startActivity(dailIntent)
    }

    private fun initRecyclerView() {
        adapter = BuyurtmaAdapter()
        recyclerView = binding?.buyurtmaRecyclerView
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())

    }

    override fun onDetach() {
        super.onDetach()
        binding = null
        recyclerView = null
        adapter = null
        loginViewModel = null
    }
}