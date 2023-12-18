package com.airstrike.stylo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.listeners.ProductSelectionListener
import com.airstrike.stylo.models.Color
import com.airstrike.stylo.models.Shoe
import com.squareup.picasso.Picasso

class ShoeColorsAdapter(private val shoeColors: ArrayList<Color>) :
    RecyclerView.Adapter<ShoeColorsAdapter.ColorViewHolder>() {

    inner class ColorViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val colorPicker : Button

        init {
            colorPicker = view.findViewById(R.id.shoeColorPicker)
        }

        fun bind(color : Color) {
            colorPicker.setBackgroundColor(android.graphics.Color.parseColor(color.HexValue))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ColorViewHolder {
        val colorsViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.shoe_color_picker_layout, parent, false)
        return ColorViewHolder(colorsViewHolder)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(shoeColors[position])
    }

    override fun getItemCount(): Int {
        return shoeColors.size
    }
}