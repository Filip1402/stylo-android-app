package com.airstrike.stylo.listeners

import com.airstrike.stylo.models.Shoe

interface ProductSelectionListener {
    fun openProductDetail(shoe : Shoe)
}