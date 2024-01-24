package com.airstrike.stylo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.listeners.CartItemListener
import com.airstrike.stylo.models.CartItem
import com.squareup.picasso.Picasso

class CheckoutItemAdapter(private val cartItemsList: MutableList<CartItem>) :
    RecyclerView.Adapter<CheckoutItemAdapter.CheckoutItemViewHolder>() {

    inner class CheckoutItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val removeItemBtn : ImageButton
        private val shoeDetails: TextView
        private val cartMinusBtn: ImageButton
        private val cartPlusBtn: ImageButton
        private val cartItemQuantity: TextView
        private var cartItemImage: ImageView
        private val cartItemTotal: TextView
        private val cartItemPricePerPiece: TextView
        init {
            removeItemBtn = view.findViewById(R.id.cartItemRemoveButton)
            shoeDetails = view.findViewById(R.id.cartItemDetails)
            cartMinusBtn = view.findViewById(R.id.cartMinusButton)
            cartPlusBtn = view.findViewById(R.id.cartPlusButton)
            cartItemQuantity = view.findViewById(R.id.cartItemQuantity)
            cartItemImage = view.findViewById(R.id.cartItemPhoto)
            cartItemTotal = view.findViewById(R.id.cartItemTotal)
            cartItemPricePerPiece = view.findViewById(R.id.cartItemPricePerPiece)
        }

        fun bind(item: CartItem) {

            shoeDetails.text =
                item.shoe.Manufacturer+
                        " "+
                        item.shoe.Model +
                        ", " +
                        item.color.Name+
                        ", " +
                        item.shoeSize.size

            Picasso.with(cartItemImage.context)
                .load(item.shoe.ImageUrls[0])
                .into(cartItemImage)


            cartItemQuantity.text = item.quantity.toString()
            displayPriceInfo(item.quantity,item.shoe.Price)
        }
        private fun displayPriceInfo(quantity : Int,price : Double) {
            cartItemTotal.text = String.format("%.2f",(quantity * price)) + " EUR"
            cartItemPricePerPiece.text = view.context.getString(R.string.price_per_piece) + " " +price.toString() + " EUR"
        }
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckoutItemViewHolder {
        val cartItemView =
            LayoutInflater.from(parent.context).inflate(R.layout.checkout_item_layout, parent, false)
        return CheckoutItemViewHolder(cartItemView)
    }
    override fun onBindViewHolder(holder: CheckoutItemViewHolder, position: Int) {
        holder.bind(cartItemsList[position])
    }
    override fun getItemCount(): Int {
        return cartItemsList.size
    }

}