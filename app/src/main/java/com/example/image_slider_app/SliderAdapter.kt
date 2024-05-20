package com.example.image_slider_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewpager2.widget.ViewPager2
import com.makeramen.roundedimageview.RoundedImageView

class SliderAdapter internal constructor(
    sliderItems: MutableList<SliderItem>,
    viewPager: ViewPager2
): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>(){

    private val sliderItems : List<SliderItem>
    private val viewPager2 : ViewPager2

    private val runnable = Runnable{
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }

    init {
        this.sliderItems = sliderItems
        this.viewPager2 = viewPager
    }

    class SliderViewHolder(itemView:View): ViewHolder(itemView){
        private val imageView:RoundedImageView = itemView.findViewById(R.id.imageSlide)

        fun image(sliderItem: SliderItem){
            imageView.setImageResource(sliderItem.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.image(sliderItems[position])
        if (position == sliderItems.size - 2){
            viewPager2.post(runnable)
        }

    }

    override fun getItemCount(): Int {
        return sliderItems.size
    }
}