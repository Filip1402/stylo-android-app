package com.airstrike.stylo.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.models.Shoe
import com.squareup.picasso.Picasso


class ShoesAdapter(private val bouquetsList: ArrayList<Shoe>) :
    RecyclerView.Adapter<ShoesAdapter.ShoeViewHolder>() {


    inner class ShoeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val shoeBrand: TextView
        private val shoeModel: TextView
        private val shoePrice: TextView
        private var shoeImage: ImageView

        init {
            shoeBrand = view.findViewById(R.id.tv_productModel)
            shoeModel = view.findViewById(R.id.tv_productName)
            shoePrice = view.findViewById(R.id.tv_productPrice)
            shoeImage = view.findViewById(R.id.image_view)
        }

        fun bind(shoe: Shoe) {
            shoeBrand.text = shoe.Manufacturer
            shoeModel.text = shoe.Model
            shoePrice.text = shoe.Price.toString() + " EUR"

            Picasso.with(shoeImage.context)
                .load(shoe.ImageUrls[0])
                .into(shoeImage, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        Log.i("Picasso","Success");
                    }

                    override fun onError() {
                        Log.i("PicassoError","Couldnt load pic from url");
                        shoeImage.setImageResource(R.drawable.noimage)
                    }

                })
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShoeViewHolder {
        val bouquetsView =
            LayoutInflater.from(parent.context).inflate(R.layout.product_card_layout, parent, false)
        return ShoeViewHolder(bouquetsView)
    }

    override fun onBindViewHolder(holder: ShoeViewHolder, position: Int) {
        holder.bind(bouquetsList[position])
    }

    override fun getItemCount(): Int {
        return bouquetsList.size
    }
}