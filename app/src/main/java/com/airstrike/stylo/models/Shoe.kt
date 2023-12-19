package com.airstrike.stylo.models

data class Shoe(
    val Id : String,
    val Manufacturer : String,
    val Model : String,
    val Price  : Double,
    val Available : Boolean,
    val ImageUrls : List<String>,
    val Variants : List<Shoe>?
) {
}