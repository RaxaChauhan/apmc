package com.example.apmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TableRowAdapter (private var userArrayList: List<Item>) :
    RecyclerView.Adapter<TableRowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.table_row, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.txtname.text = userArrayList[i].name
        viewHolder.txthighPrice.text = userArrayList[i].max_price_guj
        viewHolder.txtlowPrice.text = userArrayList[i].min_price_guj
        viewHolder.txtnormalPrice.text = userArrayList[i].avg_price_guj
    }

    override fun getItemCount(): Int {
        return userArrayList.size
    }
    fun clearData() {
        userArrayList = emptyList()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtname: TextView = itemView.findViewById(R.id.janshi)
        val txthighPrice: TextView = itemView.findViewById(R.id.highPrice)
        val txtlowPrice: TextView = itemView.findViewById(R.id.lowPrice)
        val txtnormalPrice: TextView = itemView.findViewById(R.id.normalPrice)


    }
}