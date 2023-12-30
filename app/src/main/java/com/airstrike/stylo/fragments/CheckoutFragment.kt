package com.airstrike.stylo.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.adapters.AddressesAdapter
import com.airstrike.stylo.helpers.AddressDetailsDialogHandler
import com.airstrike.stylo.listeners.AddressChangeListener
import com.airstrike.stylo.listeners.AddressSelectionListener
import com.airstrike.stylo.models.Address


class CheckoutFragment : Fragment(), AddressChangeListener{

    private lateinit var shippingAddressesRv : RecyclerView
    private lateinit var billingAddressesRv : RecyclerView
    private lateinit var addAddressBtn : TextView
    private lateinit var selectedShippingAddress : Address
    private var addresses = mutableListOf<Address>(
        Address(
            id = "1",
            firstName = "Ana",
            lastName = "Horvat",
            streetName = "Trg Bana Jelačića",
            streetNumber = "15",
            additionalStreetInfo = "Apartment 3",
            postalCode = "10000",
            city = "Zagreb",
            country = "HR",
            phone = "+385912345678",
    ),Address(
            id = "2",
            firstName = "Marko",
            lastName = "Kovačić",
            streetName = "Primorska ulica",
            streetNumber = "23",
            additionalStreetInfo = "",
            postalCode = "51000",
            city = "Rijeka",
            country = "HR",
            phone = "+385987654321",
        ),Address(
            id = "3",
            firstName = "Ivana",
            lastName = "Babić",
            streetName = "Ulica Kralja Tomislava",
            streetNumber = "8",
            additionalStreetInfo = "Floor 2",
            postalCode = "32000",
            city = "Vukovar",
            country = "HR",
            phone = "+385998877665",
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_checkout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        shippingAddressesRv = view.findViewById(R.id.shipping_address_rv)
        billingAddressesRv = view.findViewById(R.id.billing_address_rv)
        addAddressBtn = view.findViewById(R.id.checkout_add_shipping_address)

        shippingAddressesRv.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        shippingAddressesRv.adapter = AddressesAdapter(addresses,object : AddressSelectionListener{
            override fun notifyAddressSelectionChanged(address: Address) {
                selectedShippingAddress = address
            }
        },this)

        handleAddAddresBtnClick(view)

    }

    private fun handleAddAddresBtnClick(view: View) {
        addAddressBtn.setOnClickListener {
            val dialogView = LayoutInflater.from(view.context).inflate(R.layout.address_details_layout, null)
            val addressDialogHandler =  AddressDetailsDialogHandler(dialogView)
            val alertDialog =
                AlertDialog.Builder(view.context)
                    .setTitle(R.string.edit_address)
                    .setView(dialogView)
                    .setPositiveButton(view.resources.getString(R.string.save),null)
                    .setNegativeButton(view.resources.getString(R.string.cancel)){ _, _ ->
                    }
                    .show()
            val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {

                if(addressDialogHandler.checkIfRequiredDataIsProvided() == true)
                {
                    notifyAddressAddition(addressDialogHandler.getAddress())
                    alertDialog.dismiss()
                }
                else {
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

    override fun notifyAddressAddition(address: Address) {
        addresses.add(0,address)
        shippingAddressesRv.adapter?.notifyDataSetChanged()
        billingAddressesRv.adapter?.notifyDataSetChanged()
        TODO("Send data to server")
    }

    override fun notifyAddressUpdate(oldAddress: Address, newAddress: Address) {
        val index = addresses.indexOf(oldAddress)
        addresses.remove(oldAddress)
        addresses.add(index,newAddress)
    }


}