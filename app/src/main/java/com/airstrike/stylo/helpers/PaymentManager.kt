package com.airstrike.stylo.helpers

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.airstrike.stylo.listeners.PaymentOutcomeListener
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.fuel.json.responseJson
import com.github.kittinunf.result.Result

import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult

class PaymentManager {

    lateinit var paymentSheet: PaymentSheet
    lateinit var customerConfig: PaymentSheet.CustomerConfiguration
    lateinit var paymentIntentClientSecret: String
    var context : Context
    val listener : PaymentOutcomeListener
    constructor(fragment: Fragment,total : Double, listener : PaymentOutcomeListener)
    {
        this.listener = listener
        this.context = fragment.requireContext()
        paymentSheet = PaymentSheet(fragment, ::onPaymentSheetResult)
        getStripeKeysFromBackend(fragment, total)

    }

    private fun getStripeKeysFromBackend(fragment: Fragment, total : Double)
    {
        val amount = total * 100
        var url = "http://10.0.2.2:8000/payments/mobile/create?totalAmount="
        url +=amount.toInt()
        url.httpPost().responseJson { _, _, result ->
            if (result is Result.Success) {
                val responseJson = result.get().obj()
                paymentIntentClientSecret = responseJson.getString("paymentIntent")
                customerConfig = PaymentSheet.CustomerConfiguration(
                    responseJson.getString("customer"),
                    responseJson.getString("ephemeralKey")
                )
                val publishableKey = responseJson.getString("publishableKey")
                PaymentConfiguration.init(fragment.requireContext(), publishableKey)
            }
        }
    }
    fun presentPaymentSheet(city : String, country : String, postalCode : String) {
        paymentSheet.presentWithPaymentIntent(
            paymentIntentClientSecret,
            PaymentSheet.Configuration(
                merchantDisplayName = "Stylo",
                customer = customerConfig,
                defaultBillingDetails = PaymentSheet.BillingDetails(
                    PaymentSheet.Address(
                        city,
                        country,
                        null,
                        null,
                        postalCode,
                        null,),
                    null,
                    null,
                    null
                ),
                allowsDelayedPaymentMethods = true
            )
        )
    }

    private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
        when (paymentSheetResult) {
            is PaymentSheetResult.Canceled -> {
                print("Canceled")
            }

            is PaymentSheetResult.Failed -> {
                print("Error: ${paymentSheetResult.error}")
                listener.onPaymentFailure()
            }

            is PaymentSheetResult.Completed -> {
                listener.onPaymentSuccess()

            }
        }
    }
}