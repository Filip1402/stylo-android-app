package com.airstrike.stylo.fragments

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.core.authentification.LoggedInUser
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.stylo.ProcessingActivity
import com.airstrike.stylo.R
import com.airstrike.stylo.ShoppingActivity
import com.airstrike.stylo.adapters.AddressesAdapter
import com.airstrike.stylo.helpers.AddressDetailsDialogHandler
import com.airstrike.stylo.helpers.PaymentManager
import com.airstrike.stylo.helpers.SecurePreferencesManager
import com.airstrike.stylo.listeners.AddressChangeListener
import com.airstrike.stylo.listeners.AddressSelectionListener
import com.airstrike.stylo.listeners.PaymentOutcomeListener
import com.airstrike.stylo.models.Address
import com.airstrike.stylo.models.CartItem
import com.airstrike.web_services.models.responses.CustomersAddresses
import com.airstrike.web_services.models.responses.JWT
import com.airstrike.web_services.network.request_handler.AddressesRequestHandler
import com.airstrike.web_services.network.request_handler.JwtRequestHandler


class CheckoutFragment : Fragment(), AddressChangeListener {

    private lateinit var shippingAddressesRv: RecyclerView
    private lateinit var billingAddressesRv: RecyclerView
    private lateinit var addAddressBtn: TextView
    private lateinit var selectedShippingAddress: Address
    private lateinit var selectedBillingAddress: Address
    private var addresses = mutableListOf<Address>()
    private var cart = arrayListOf<CartItem>()
    private lateinit var paymentManager : PaymentManager
    private lateinit var payBtn : Button
    private  var fragmentTag : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCustomerAddresses("")
        getCartItems()?.let {cart = it}
        Log.i("cart",cart.toString())
        fragmentTag = this.tag

        setupPayment()
    }

    private fun setupPayment() {
        var total = 0.0
        cart.forEach {
            total+= it.quantity * it.shoe.Price
        }
        paymentManager = PaymentManager(this,total,object : PaymentOutcomeListener{
            override fun onPaymentSuccess() {
                SecurePreferencesManager(requireContext()).deleteData("cart")
                Toast.makeText(requireContext(), "Successful payment",Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), ShoppingActivity::class.java))

            }

            override fun onPaymentFailure() {
                Toast.makeText(requireContext(), "Payment error",Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getCartItems(): ArrayList<CartItem>? {
        return SecurePreferencesManager(requireContext()).getObjects("cart", CartItem::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shippingAddressesRv = view.findViewById(R.id.shipping_address_rv)
        billingAddressesRv = view.findViewById(R.id.billing_address_rv)
        addAddressBtn = view.findViewById(R.id.checkout_add_shipping_address)
        payBtn = view.findViewById(R.id.paybtn)

        shippingAddressesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        shippingAddressesRv.adapter =
            AddressesAdapter(addresses, object : AddressSelectionListener {
                override fun notifyAddressSelectionChanged(address: Address) {
                    selectedShippingAddress = address
                    Log.i("shipping", selectedShippingAddress.toString())
                }
            }, this)

        billingAddressesRv.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        billingAddressesRv.adapter = AddressesAdapter(addresses, object : AddressSelectionListener {
            override fun notifyAddressSelectionChanged(address: Address) {
                selectedBillingAddress = address
                Log.i("billing", selectedBillingAddress.toString())
            }
        }, this)

        handleAddAddresBtnClick(view)

        payBtn.setOnClickListener {
            paymentManager.presentPaymentSheet(
                selectedBillingAddress.city,
                selectedBillingAddress.country,
                selectedBillingAddress.postalCode)
        }
    }

    private fun handleAddAddresBtnClick(view: View) {
        addAddressBtn.setOnClickListener {
            val dialogView =
                LayoutInflater.from(view.context).inflate(R.layout.address_details_layout, null)
            val addressDialogHandler = AddressDetailsDialogHandler(dialogView)
            val alertDialog =
                AlertDialog.Builder(view.context)
                    .setTitle(R.string.edit_address)
                    .setView(dialogView)
                    .setPositiveButton(view.resources.getString(R.string.save), null)
                    .setNegativeButton(view.resources.getString(R.string.cancel)) { _, _ ->
                    }
                    .show()
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {

                if (addressDialogHandler.checkIfRequiredDataIsProvided() == true) {
                    notifyAddressAddition(addressDialogHandler.getAddress())
                    alertDialog.dismiss()
                } else {
                    Toast.makeText(
                        view.context,
                        R.string.missing_addres_data,
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            return@setOnClickListener
        }
    }

    private fun getJWTFromBackend() {
        JwtRequestHandler().sendRequest(object : ResponseListener<JWT> {
            override fun onSuccess(response: JWT) {
                getCustomerAddresses(response.token)
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(requireContext(), "Error getting hero image", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getCustomerAddresses(jwt: String) {
        val user = SecurePreferencesManager(requireContext()).getObject(
            "loggedInUser",
            LoggedInUser::class.java
        ) as LoggedInUser

        AddressesRequestHandler(user.email, jwt).sendRequest(object :
            ResponseListener<CustomersAddresses> {
            override fun onSuccess(response: CustomersAddresses) {
                response.addresses.forEach {
                    addresses.add(
                        Address(
                            it.id,
                            it.firstName,
                            it.lastName,
                            it.streetName,
                            it.streetNumber,
                            it.additionalStreetInfo,
                            it.postalCode,
                            it.city,
                            it.country,
                            it.phone
                        )
                    )
                }
                if(!::selectedBillingAddress.isInitialized)
                    selectedBillingAddress = addresses[0]
                if(addresses.size == 0)
                    Toast.makeText(requireContext(),"Add address",Toast.LENGTH_LONG).show()
                shippingAddressesRv.adapter?.notifyDataSetChanged()
                billingAddressesRv.adapter?.notifyDataSetChanged()
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(requireContext(), "Error getting hero image", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }
        })

    }

    override fun notifyAddressAddition(address: Address) {
        addresses.add(0, address)
        shippingAddressesRv.adapter?.notifyDataSetChanged()
        billingAddressesRv.adapter?.notifyDataSetChanged()
        //Send data to server
    }

    override fun notifyAddressUpdate(oldAddress: Address, newAddress: Address) {
        val index = addresses.indexOf(oldAddress)
        if (index == -1)
            return
        addresses[index] = newAddress
        shippingAddressesRv.adapter?.notifyDataSetChanged()
        billingAddressesRv.adapter?.notifyDataSetChanged()

    }
}