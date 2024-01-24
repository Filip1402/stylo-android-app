package com.airstrike.stylo.listeners

import com.airstrike.stylo.models.Color
import com.airstrike.stylo.models.Shoe
import com.airstrike.stylo.models.ShoeSize

interface ProductSelectionListener {
    fun openProductDetail(shoe : Shoe)

    fun getProductVariantByColor(variantColor : Color)

    fun getProductVariantSize(size : ShoeSize)
}