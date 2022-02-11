package com.example.buyurtma.ui.home.statistika

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.buyurtma.R
import com.example.buyurtma.databinding.FragmentStatistikaBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class StatistikaFragment : Fragment() {
    private var binding: FragmentStatistikaBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatistikaBinding.bind(
            inflater.inflate(
                R.layout.fragment_statistika,
                container,
                false
            )
        )
        initBarChart()

        // Inflate the layout for this fragment
        return binding?.root
    }

    private fun initBarChart() {
        var list = mutableListOf<Double>()
        var listData = mutableListOf<BarEntry>()

        for (it in 1..20) {
            list.add(it * 1.0)
        }

        for (it in list) {
            listData.add(BarEntry(it.toFloat() + 1.0F, it.toFloat()))
        }
        var barDataSet = BarDataSet(listData,"Diriver")
        barDataSet.color = ContextCompat.getColor(requireContext(),R.color.chart_bar_color)
        var barData = BarData(barDataSet)
        binding?.barChart?.data = barData
        binding?.barChart?.invalidate()
    }

    override fun onDetach() {
        super.onDetach()
        binding = null
    }
}