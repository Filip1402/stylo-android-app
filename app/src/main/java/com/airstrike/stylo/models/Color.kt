package com.airstrike.stylo.models

import java.util.ArrayList

 class Color
{
    val Name : String
    var HexValue = ""
    val colors = listOf(
        mapOf("name" to "bijela", "hexValue" to "#FFFFFF"),
        mapOf("name" to "siva", "hexValue" to "#494646"),
        mapOf("name" to "plava", "hexValue" to "#0D5CD2"),
        mapOf("name" to "crna", "hexValue" to "#090909"),
        mapOf("name" to "smeđa", "hexValue" to "#783F00"),
        mapOf("name" to "narančasta", "hexValue" to "#FF8D23"),
        mapOf("name" to "žuta", "hexValue" to "#F1CD0C"),
        mapOf("name" to "zelena", "hexValue" to "#0D873E"),
        mapOf("name" to "crvena", "hexValue" to "#D40000"),
        mapOf("name" to "roza", "hexValue" to "#bd3391")
    )


    constructor(name : String)
    {
        Name = name
        val matchingPair = colors.find { it["name"] == name.lowercase() }
        HexValue = matchingPair!!.get("hexValue")!!
    }



}

