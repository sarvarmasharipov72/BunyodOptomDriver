package com.example.buyurtma.ui.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs


import androidx.navigation.ui.NavigationUI

import com.example.buyurtma.R
import com.example.buyurtma.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    val homeViewModel: HomeViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.bind(inflater.inflate(R.layout.fragment_home, container, false))
        val bottomNavigationView = binding?.bottomNavigationView

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        navHostFragment.navController

        if (bottomNavigationView != null) {
            NavigationUI.setupWithNavController(bottomNavigationView,navHostFragment.navController)
        }


        homeViewModel.itemData.observe(viewLifecycleOwner, {

        })

//        bottomNavigationView?.setupWithNavController(navController)

        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }
}