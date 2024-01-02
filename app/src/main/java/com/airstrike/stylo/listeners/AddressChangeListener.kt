package com.airstrike.stylo.listeners

import com.airstrike.stylo.models.Address

interface AddressChangeListener {
    fun notifyAddressAddition(address : Address)

    fun notifyAddressUpdate(oldAddress : Address,newAddress : Address)
}