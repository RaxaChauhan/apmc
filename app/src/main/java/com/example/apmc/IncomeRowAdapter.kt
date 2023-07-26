package com.example.apmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class IncomeRowAdapter(private var incomeArrayList: List<Item>, private val incomeType: Int) : RecyclerView.Adapter<IncomeRowAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.incomerow, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        when (incomeType) {
            0 -> {
                viewHolder.txtpakname.text = incomeArrayList[i].name
                viewHolder.txtIncome.text = incomeArrayList[i].in_qtn_guj
                viewHolder.txtweight.text = incomeArrayList[i].guni_guj
            }
            1 -> {
                viewHolder.txtpakname.text = incomeArrayList[i].name
                viewHolder.txtIncome.text = incomeArrayList[i].dagina_guj
                viewHolder.txtweight.text = incomeArrayList[i].kg_guj
            }
        }

    }

    override fun getItemCount(): Int {
        return incomeArrayList.size
    }

    fun clearData() {
        incomeArrayList = emptyList()
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtpakname: TextView = itemView.findViewById(R.id.pakname)
        val txtIncome: TextView = itemView.findViewById(R.id.Income)
        val txtweight: TextView = itemView.findViewById(R.id.weight)
    }
}