package com.airstrike.stylo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.models.Color
import com.airstrike.stylo.models.ShoeSize

class ShoeSizesAdapter(private val shoeSizes: ArrayList<ShoeSize>) :
    RecyclerView.Adapter<ShoeSizesAdapter.ShoeSizeViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    inner class ShoeSizeViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val shoeSizePicker : AppCompatButton

        init {
            shoeSizePicker = view.findViewById(R.id.sizePickerButton)
        }

        fun bind(shoeSize : ShoeSize) {
            shoeSizePicker.text = shoeSize.size.toString()
            shoeSizePicker.setBackgroundResource(R.drawable.corner_radius)
            shoeSizePicker.setOnClickListener {

                if (layoutPosition != RecyclerView.NO_POSITION) {
                    it.setBackgroundResource(R.drawable.blue_corner_radius)
                    setSelectedPosition(layoutPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoeSizeViewHolder {
        val sizesViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.shoe_size_picker_layout, parent, false)
        return ShoeSizeViewHolder(sizesViewHolder)
    }

    override fun onBindViewHolder(holder: ShoeSizeViewHolder, position: Int) {
        holder.bind(shoeSizes[position])
    }

    override fun getItemCount(): Int {
        return shoeSizes.size
    }
    fun setSelectedPosition(position: Int) {
        if (selectedPosition != position) {
            val previousSelected = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelected)
        }
    }
}