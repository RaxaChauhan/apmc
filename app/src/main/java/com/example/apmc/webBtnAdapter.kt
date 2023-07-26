package com.example.apmc

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class webBtnAdapter (private val webLinks: List<Item>) : RecyclerView.Adapter<webBtnAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.webbtn, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val webLink = webLinks[i].url
        holder.button.text = webLinks[i].name
        holder.button.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(webLink))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return webLinks.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.WebBtn)
    }
}