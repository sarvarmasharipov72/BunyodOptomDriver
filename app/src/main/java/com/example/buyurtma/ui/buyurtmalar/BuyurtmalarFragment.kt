package com.example.buyurtma.ui.buyurtmalar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buyurtma.R
import com.example.buyurtma.databinding.FragmentBuyurtmalarBinding
import com.example.buyurtma.ui.home.HomeViewModel
import com.example.buyurtma.ui.login.ViewModel.LoginViewModel

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

        binding = FragmentBuyurtmalarBinding.bind(inflater.inflate(R.layout.fragment_buyurtmalar, container, false))

        binding?.imageButtonBuyurtma?.setOnClickListener {
            findNavController().navigate(R.id.action_buyurtmalarFragment_to_homeSubFragment)
        }
        initRecyclerView()
        loginViewModel?.getData("Bearer ${homeViewModel.token.value.toString()}", navArgs.order._id)

        loginViewModel?.order?.observe(this, {
            if (it.isSuccessful) {
                adapter?.setData(it.body()?.data!!)
            }
        })

        // Inflate the layout for this fragment
        return binding?.root
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