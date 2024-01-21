package com.airstrike.stylo.helpers

import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import com.airstrike.stylo.R
import com.airstrike.stylo.models.Address

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
    constructor(view : View, currentAddress : Address)
    {
        View = view
        CurrentAddress = currentAddress
        supportedCountries = listOf(View.context.getString(R.string.chose_country),"HR","UK","IT","US")
        bindUI(View)
        loadData(CurrentAddress!!)
    }

    constructor(view : View)
    {
        View = view
        supportedCountries = listOf(View.context.getString(R.string.chose_country),"HR","UK","IT","US")
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
        return Address("",
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
}