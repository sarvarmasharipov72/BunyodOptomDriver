package com.example.buyurtma.ui.buyurtmalar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.buyurtma.R
import com.example.buyurtma.ui.buyurtmalar.model.Data
import com.example.buyurtma.ui.buyurtmalar.model.Product
import java.lang.StringBuilder

class BuyurtmaAdapter : RecyclerView.Adapter<BuyurtmaAdapter.BuyurtmaHolder>() {
    private val HEADER = 0
    private var list = emptyList<Data>()

    open class BuyurtmaHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class BuyurtmaHolderHeader(itemView: View) : BuyurtmaHolder(itemView) {
        private val buyurtmaRaqami = itemView.findViewById<TextView>(R.id.buyurtmaRaqami)
        private val mahsulotSoni = itemView.findViewById<TextView>(R.id.mahsulotSoni)
        private val umumiySumma = itemView.findViewById<TextView>(R.id.umumiySumma)
        private val manzil = itemView.findViewById<TextView>(R.id.manzil)
        private val oluvchi = itemView.findViewById<TextView>(R.id.oluvchi)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val time = itemView.findViewById<TextView>(R.id.time)

        fun bind(order: Data) {
            buyurtmaRaqami.text = order.order.toString()
            mahsulotSoni.text = "${order.count} ta"
            umumiySumma.text = "${priceChange(order.amount.toString())} so'm"
            manzil.text = "${order.address}"
            oluvchi.text = order.user.name
            date.text = order.createdAt.substring(0, 10)
            time.text = order.createdAt.substring(11, 16)

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

    class BuyurtmaHolderBody(itemView: View) : BuyurtmaHolder(itemView) {
        private val producteName = itemView.findViewById<TextView>(R.id.producteName)
        private val countBox = itemView.findViewById<TextView>(R.id.countBox)
        private val price = itemView.findViewById<TextView>(R.id.price)

        fun bind(product: Product) {
            producteName.text = product.product.name
            countBox.text = "${product.count} ta"
            price.text = "${priceChange(product.amount.toString())} So'm"
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyurtmaHolder {
        return when (viewType) {
            HEADER -> BuyurtmaHolderHeader(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.buyurtma_header_item, parent, false)
            )
            else -> BuyurtmaHolderBody(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.buyurtma_body_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: BuyurtmaHolder, position: Int) {
        when (holder) {
            is BuyurtmaHolderHeader -> holder.bind(list[0])
            is BuyurtmaHolderBody -> holder.bind(list[0].product[position - 1])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> HEADER
            else -> position
        }
    }

    override fun getItemCount(): Int {
        if (list.isNotEmpty())
            return list[0].product.size + 1
        return 0
    }

    fun setData(list: List<Data>) {
        this.list = list
        notifyDataSetChanged()
    }
}