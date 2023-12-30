package com.airstrike.stylo.adapters

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.graphics.drawable.StateListDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.listeners.ProductSelectionListener
import com.airstrike.stylo.models.Color
import com.airstrike.stylo.models.Shoe
import com.squareup.picasso.Picasso

class ShoeColorsAdapter(private val shoeColors: ArrayList<Color>, private val variantSelectionCallback : ProductSelectionListener) :
    RecyclerView.Adapter<ShoeColorsAdapter.ColorViewHolder>() {

    private var selectedPosition = 0

    inner class ColorViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val colorPicker : AppCompatButton

        init {
            colorPicker = view.findViewById(R.id.shoeColorPicker)
        }

        fun bind(color : Color) {

            setButtonStyle(colorPicker, color.HexValue, if (layoutPosition == selectedPosition) "#1443BB" else "#BEBEBE")
            colorPicker.setOnClickListener {
                variantSelectionCallback.getProductVariantByColor(color)
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    setButtonStyle(colorPicker,color.HexValue,"#1443BB")
                    setSelectedPosition(layoutPosition)
                }
            }
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
    fun setSelectedPosition(position: Int) {
        if (selectedPosition != position) {
            val previousSelected = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelected)
        }
    }

    private fun setButtonStyle(colorPicker: AppCompatButton, innerColorHex: String,outlineColorHex : String ) {
        val backgroundColor = android.graphics.Color.parseColor(innerColorHex)
        val outlineColor = android.graphics.Color.parseColor(outlineColorHex)
        val backgroundDrawable = GradientDrawable()
        backgroundDrawable.setStroke(10,outlineColor)
        backgroundDrawable.setColor(backgroundColor)
        backgroundDrawable.cornerRadius = 22f
        colorPicker.setBackground(backgroundDrawable)

    }

}