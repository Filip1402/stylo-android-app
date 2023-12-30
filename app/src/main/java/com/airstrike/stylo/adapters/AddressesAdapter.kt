package com.airstrike.stylo.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.helpers.AddressDetailsDialogHandler
import com.airstrike.stylo.listeners.AddressChangeListener
import com.airstrike.stylo.listeners.AddressSelectionListener
import com.airstrike.stylo.models.Address


class AddressesAdapter(private val addresses: MutableList<Address>, private val addressListener: AddressSelectionListener, private val addressAdditionListener: AddressChangeListener) :
    RecyclerView.Adapter<AddressesAdapter.AddressViewHolder>() {

    private var selectedPosition = 0
    inner class AddressViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private var customerName : TextView
        private var street : TextView
        private var city : TextView
        private var editBtn : ImageButton

        init {
            customerName = view.findViewById(R.id.address_full_name)
            street = view.findViewById(R.id.address_street)
            city = view.findViewById(R.id.address_city_state)
            editBtn = view.findViewById(R.id.address_edit)
        }
        fun bind(address : Address) {
            customerName.text = address.firstName + " " + address.lastName
            street.text = address.streetName + " " + address.streetNumber
            city.text = address.postalCode + " " + address.city + " " + address.country
            view.setBackgroundResource(if (layoutPosition == selectedPosition) R.drawable.blue_corner_radius  else  R.drawable.corner_radius)

            view.setOnClickListener {
                addressListener.notifyAddressSelectionChanged(address)
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    it.setBackgroundResource(R.drawable.blue_corner_radius)
                    setSelectedPosition(layoutPosition)
                }
            }



            editBtn.setOnClickListener {
                val dialogView = LayoutInflater.from(view.context).inflate(R.layout.address_details_layout, null)
                val addressDialogHandler =  AddressDetailsDialogHandler(dialogView,address)
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
                        addressAdditionListener.notifyAddressUpdate(address,addressDialogHandler.getAddress())
                        notifyDataSetChanged();
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
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddressViewHolder {
        val addressesViewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.address_layout, parent, false)
        return AddressViewHolder(addressesViewHolder)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(addresses[position])
    }

    override fun getItemCount(): Int {
        return addresses.size
    }

    fun setSelectedPosition(position: Int) {
        if (selectedPosition != position) {
            val previousSelected = selectedPosition
            selectedPosition = position
            notifyItemChanged(previousSelected)
        }
    }

}