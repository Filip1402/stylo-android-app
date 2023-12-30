package com.airstrike.stylo.listeners

import com.airstrike.stylo.models.Address

interface AddressListener {
    fun notifyAddressSelectionChanged(address: Address)
}