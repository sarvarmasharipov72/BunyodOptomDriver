package com.example.buyurtma.ui.home.home_sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.buyurtma.R
import com.example.buyurtma.Repository
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.home.home_sub.model.Data
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModelFactory

class HomeSubFragment : Fragment() {

    val homeViewModel: HomeViewModel by activityViewModels()

    private var loginViewModel: LoginViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home_sub, container, false)

        loginViewModel = ViewModelProvider(
            this,
            LoginViewModelFactory(Repository())
        ).get(LoginViewModel::class.java)


        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecyclerView)
        val adapter = HomeAdapter {
            clickListener(it)
        }
        recyclerView.adapter = adapter



        return view
    }

    fun clickListener(orders: Data) {
        val action = HomeSubFragmentDirections.actionHomeSubFragmentToBuyurtmalarFragment(orders)
        findNavController().navigate(action)
    }

}