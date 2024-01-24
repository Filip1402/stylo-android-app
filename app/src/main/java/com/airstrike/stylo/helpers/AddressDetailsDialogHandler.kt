package com.airstrike.stylo.helpers

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.airstrike.core.authentification.LoggedInUser
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.stylo.R
import com.airstrike.stylo.models.Address
import com.airstrike.web_services.models.AddAddressRequestBody
import com.airstrike.web_services.models.ShippingAddress
import com.airstrike.web_services.models.UpdateAddressRequestBody
import com.airstrike.web_services.models.responses.AddedAddress
import com.airstrike.web_services.network.request_handler.AddAddressRequestHandler
import com.airstrike.web_services.network.request_handler.UpdateAddressRequestHandler

class AddressDetailsDialogHandler {
    private val View : View
    private var CurrentAddress : Address? = null
    private lateinit var countrySelector : Spinner
    private lateinit var firstName : EditText
    private lateinit var lastName : EditText
    private lateinit var streetName : EditText
    private lateinit var houseNumber : EditText
    private lateinit var additionalAddresInfo : EditText
    private lateinit var postalNumber : EditText
    private lateinit var city : EditText
    private lateinit var phoneNumber : EditText
    private val supportedCountries  : List<String> //get them from commercetools
    private var customer : LoggedInUser
    constructor(view : View, currentAddress : Address)
    {
        this.customer = SecurePreferencesManager(view.context).getObject("loggedInUser", com.airstrike.core.authentification.LoggedInUser::class.java) as com.airstrike.core.authentification.LoggedInUser

        View = view
        CurrentAddress = currentAddress
        supportedCountries = listOf(View.context.getString(R.string.chose_country),"HR","UK","IT","US")
        bindUI(View)
        loadData(CurrentAddress!!)
    }

    constructor(view : View)
    {
        this.customer = SecurePreferencesManager(view.context).getObject("loggedInUser", com.airstrike.core.authentification.LoggedInUser::class.java) as com.airstrike.core.authentification.LoggedInUser
        View = view
        supportedCountries = listOf(View.context.getString(R.string.chose_country),"HR")
        bindUI(View)
    }

    private fun loadData(currentAddress: Address) {
        val index = supportedCountries.indexOf(currentAddress.country)
        countrySelector.setSelection(index,true)
        firstName.setText(currentAddress.firstName)
        lastName.setText(currentAddress.lastName)
        streetName.setText(currentAddress.streetName)
        houseNumber.setText(currentAddress.streetNumber)
        additionalAddresInfo.setText(currentAddress.additionalStreetInfo)
        postalNumber.setText(currentAddress.postalCode)
        city.setText(currentAddress.city)
        phoneNumber.setText(currentAddress.phone)
    }

    private fun bindUI(view : View) {
        countrySelector = view.findViewById(R.id.address_country_spn)
        firstName = view.findViewById(R.id.address_first_name_et)
        lastName = view.findViewById(R.id.address_last_name_et)
        streetName = view.findViewById(R.id.address_street_name_et)
        houseNumber = view.findViewById(R.id.address_house_number_et)
        additionalAddresInfo = view.findViewById(R.id.address_street_info_et)
        postalNumber = view.findViewById(R.id.address_postal_number_et)
        city = view.findViewById(R.id.address_city_et)
        phoneNumber = view.findViewById(R.id.address_phone_et)
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            View.context,
            android.R.layout.simple_spinner_item,
            supportedCountries
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        countrySelector.adapter = adapter
    }

    fun checkIfRequiredDataIsProvided() : Boolean{
        return firstName.text.isNotBlank()
                && lastName.text.isNotBlank()
                && streetName.text.isNotBlank()
                && houseNumber.text.isNotBlank()
                && postalNumber.text.isNotBlank()
                && city.text.isNotBlank()
                //&& phoneNumber.text.isNotBlank()
                && countrySelector.selectedItem as String != View.context.getString(R.string.chose_country)
    }

    fun getAddress() : Address
    {
        return Address(CurrentAddress?.id,
            firstName.text.toString(),
            lastName.text.toString(),
            streetName.text.toString(),
            houseNumber.text.toString(),
            additionalAddresInfo.text.toString(),
            postalNumber.text.toString(),
            city.text.toString(),
            countrySelector.selectedItem as String,
            phoneNumber.text.toString()
        )
    }

    fun addNewCustomerAddressOnBackend()
    {
        var address = getAddress()
        val body = AddAddressRequestBody(
            customer.id,
            customer.version,
            ShippingAddress(
                address.firstName.toString(),
                address.lastName.toString(),
                address.additionalStreetInfo.toString(),
                address.phone.toString(),
                address.streetName.toString(),
                address.streetNumber.toString(),
                address.postalCode.toString(),
                address.city.toString(),
                address.country.toString()
            )
        )
        AddAddressRequestHandler(body).sendRequest(object : ResponseListener<AddedAddress> {
            override fun onSuccess(response: AddedAddress) {
                Toast.makeText(View.context, "Address added", Toast.LENGTH_SHORT)
                    .show()
                customer.version = response.data.version
                SecurePreferencesManager(View.context).saveObject("loggedInUser",customer)
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(View.context, "Error adding new address", Toast.LENGTH_LONG)
                        .show()
                    Log.i("error",error)
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(View.context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun updateCustomerAddressOnBackend()
    {
        var address = getAddress()
        val body = UpdateAddressRequestBody(
            customer.id,
            customer.version,
            address.id.toString(),
            ShippingAddress(
                address.firstName.toString(),
                address.lastName.toString(),
                address.additionalStreetInfo.toString(),
                address.phone.toString(),
                address.streetName.toString(),
                address.streetNumber.toString(),
                address.postalCode.toString(),
                address.city.toString(),
                address.country.toString()
            )
        )
        UpdateAddressRequestHandler(body).sendRequest(object : ResponseListener<AddedAddress> {
            override fun onSuccess(response: AddedAddress) {
                Toast.makeText(View.context, "Address updated", Toast.LENGTH_SHORT)
                    .show()
                customer.version = response.data.version
                SecurePreferencesManager(View.context).saveObject("loggedInUser",customer)
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(View.context, "Error updating  address", Toast.LENGTH_LONG)
                        .show()
                    Log.i("error",error)
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(View.context, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

}