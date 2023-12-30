package com.airstrike.stylo.listeners

import com.airstrike.stylo.models.CartItem

interface CartItemListener {
    fun removeCartItem(item : CartItem)
    fun increaseCartItemQuantity(item : CartItem)
    fun decreaseCartItemQuantity(item : CartItem)

}