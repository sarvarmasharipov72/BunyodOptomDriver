package com.example.buyurtma.ui.home.home_sub

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.buyurtma.R
import com.example.buyurtma.ui.home.home_sub.model.Data
import java.lang.StringBuilder

class HomeAdapter(val click: (Data) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var list = emptyList<Data>()

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val conatiner = itemView.findViewById<ConstraintLayout>(R.id.containerRecycler)
        private val address = itemView.findViewById<TextView>(R.id.address)
        private val descriptionText = itemView.findViewById<TextView>(R.id.producteName)
        private val countBox = itemView.findViewById<TextView>(R.id.countBox)
        private val price = itemView.findViewById<TextView>(R.id.price)

        fun bind(orders: Data) {
            address.text = orders.order.address
            descriptionText.text = orders.user
            countBox.text = orders.order.count.toString()
            price.text = "${priceChange(orders.order.amount.toString())} so'm"

        }
        private fun priceChange(n: String): String {
            val s = StringBuilder()
            var i = 0
            while (i < n.length) {
                if (i % 3 == 0)
                    s.append(" ")
                s.append(n[i])
                i++
            }
            return s.toString()
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.home_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {

        holder.bind(list[position])
        holder.conatiner.setOnClickListener {
            click(list[position])
        }

    }

    override fun getItemCount(): Int = list.size

    fun setDataAll(list: List<Data>) {
        this.list = list
        notifyItemInserted(0)
    }

    fun setDataIndex(list: List<Data>, index: Int) {
        this.list = list
        notifyItemInserted(index)
    }

    fun removeItem(list: List<Data>, index: Int) {
        this.list = list
        notifyItemRemoved(index)
    }
}