package com.airstrike.stylo.listeners

import com.airstrike.stylo.models.Address

interface AddressSelectionListener {
    fun notifyAddressSelectionChanged(address: Address)
}