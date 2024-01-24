package com.airstrike.stylo.listeners

interface PaymentOutcomeListener {
    fun onPaymentSuccess()

    fun onPaymentFailure()
}