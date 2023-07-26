package com.example.apmc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import android.widget.ImageView
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(imageurl: ArrayList<Int>):SliderViewAdapter<SliderAdapter.SliderViewHolder>() {
    var sliderlist:ArrayList<Int> = imageurl

    override fun getCount(): Int {
        return sliderlist.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapter.SliderViewHolder {
        var inflate : View = LayoutInflater.from(parent!!.context).inflate(R.layout.slider,null)
        return SliderViewHolder(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapter.SliderViewHolder?, position: Int) {
        if(viewHolder != null){
            Glide.with(viewHolder.itemView).load(sliderlist.get(position)).fitCenter().into(viewHolder.imageView)
        }
    }
    class SliderViewHolder(itemView: View?) : SliderViewAdapter.ViewHolder(itemView) {
        var imageView: ImageView = itemView!!.findViewById(R.id.sliderimage)
    }
}