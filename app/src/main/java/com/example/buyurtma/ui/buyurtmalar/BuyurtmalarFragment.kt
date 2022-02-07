package com.example.buyurtma.ui.buyurtmalar

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.buyurtma.R
import com.example.buyurtma.databinding.FragmentBuyurtmalarBinding

class BuyurtmalarFragment : Fragment() {

    private var binding: FragmentBuyurtmalarBinding? = null
    private val navArgs: BuyurtmalarFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBuyurtmalarBinding.bind(inflater.inflate(R.layout.fragment_buyurtmalar, container, false))

        binding?.imageButtonBuyurtma?.setOnClickListener {
            findNavController().navigate(R.id.action_buyurtmalarFragment_to_homeSubFragment)
        }

        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }
}