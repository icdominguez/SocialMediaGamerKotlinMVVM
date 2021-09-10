package com.icdominguez.socialmediagamerkotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.icdominguez.socialmediagamerkotlin.databinding.SliderItemBinding
import com.icdominguez.socialmediagamerkotlin.model.SliderItem
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class SliderAdapter(context: Context, sliderItems: List<SliderItem>) : SliderViewAdapter<SliderAdapter.SliderAdapterVH>() {

    inner class SliderAdapterVH(val binding: SliderItemBinding) : SliderViewAdapter.ViewHolder(binding.root)

    var sliderItems = sliderItems
    var context = context

    override fun getCount(): Int {
        return sliderItems.size
    }

    override fun onCreateViewHolder(parent: ViewGroup) : SliderAdapterVH {
        val binding = SliderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SliderAdapterVH(binding)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapter.SliderAdapterVH?, position: Int) {

        val sliderItem = sliderItems[position]

        with(viewHolder) {
            Picasso.get().load(sliderItem.imageUrl).into(this!!.binding.imageViewSlider)
        }
    }

}