package com.airstrike.stylo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.ProcessingActivity
import com.airstrike.stylo.R
import com.airstrike.stylo.adapters.CartItemsAdapter
import com.airstrike.stylo.helpers.SecurePreferencesManager
import com.airstrike.stylo.listeners.CartItemListener
import com.airstrike.stylo.models.CartItem

class ShoppingCart : Fragment(), CartItemListener {

    private lateinit var securePreferencesManager : SecurePreferencesManager
    private lateinit var rvShopingCart : RecyclerView
    private  var cartItems = mutableListOf<CartItem>()
    private lateinit var cartTotal : TextView
    private lateinit var proceedToCheckoutBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping_cart, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cartTotal = view.findViewById(R.id.cart_total)
        proceedToCheckoutBtn = view.findViewById(R.id.cart_got_to_checkout)
        securePreferencesManager = SecurePreferencesManager(this.requireContext())
        rvShopingCart = view.findViewById(R.id.cart_items_recycler_view)
        rvShopingCart.layoutManager = LinearLayoutManager(context)
        loadShopingCartList()
        calculateCartTotal()
        proceedToCheckoutBtn.setOnClickListener {
            if(cartItems.size > 0)
                (requireActivity() as ProcessingActivity).loadFragment(CheckoutFragment())
            else
                Toast.makeText(requireContext(),"Add items, before checkout!!!",Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadShopingCartList() {
        var data = securePreferencesManager.getObjects("cart", CartItem::class.java)
        if(data != null)
        {
            cartItems = data
            rvShopingCart.adapter = CartItemsAdapter(cartItems,this)
        }
        else
            cartItems = mutableListOf<CartItem>()//empty the list
    }
    override fun removeCartItem(item: CartItem) {
        cartItems.remove(item)
        securePreferencesManager.saveObject("cart",cartItems)
        calculateCartTotal()
    }
    override fun increaseCartItemQuantity(item: CartItem) {
        cartItems.forEach{
            if(it.shoe.Id == item.shoe.Id && it.color.Name == item.color.Name && it.shoeSize.size == item.shoeSize.size) {
                it.quantity++
                securePreferencesManager.saveObject("cart",cartItems)
                calculateCartTotal()
            }
        }
    }
    override fun decreaseCartItemQuantity(item: CartItem) {
        cartItems.forEach{
            if(it.shoe.Id == item.shoe.Id && it.color.Name == item.color.Name && it.shoeSize.size == item.shoeSize.size && it.quantity > 1) {
                it.quantity--
                securePreferencesManager.saveObject("cart",cartItems)
                calculateCartTotal()
            }
        }
    }
    private fun calculateCartTotal()
    {
        var total = 0.0
        cartItems.forEach { total+=it.quantity * it.shoe.Price}
        cartTotal.text = getString(R.string.total_shopping_cart) + " " + String.format("%.2f",total) + " EUR"
    }

}