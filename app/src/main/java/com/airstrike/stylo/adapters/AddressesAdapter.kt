package com.airstrike.stylo.adapters

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.listeners.AddressListener
import com.airstrike.stylo.models.Address


class AddressesAdapter(private val addresses: ArrayList<Address>, private val addressListener: AddressListener) :
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
                AlertDialog.Builder(view.context)
                    .setTitle(R.string.edit_address)
                    .setPositiveButton(view.resources.getString(R.string.save)){ _, _ ->
                        //notifyDataSetChanged();
                    }
                    .setNegativeButton(view.resources.getString(R.string.cancel)){ _, _ ->
                    }
                    .show()
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