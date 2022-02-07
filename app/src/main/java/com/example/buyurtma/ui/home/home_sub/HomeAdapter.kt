package com.example.buyurtma.ui.home.home_sub

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.buyurtma.R
import com.example.buyurtma.ui.home.home_sub.model.Data

class HomeAdapter(val click: (Data) -> Unit) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    var list = emptyList<Data>()

    class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val conatiner = itemView.findViewById<ConstraintLayout>(R.id.containerRecycler)

        fun bind(orders: Data) {

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
            click(list[0])
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