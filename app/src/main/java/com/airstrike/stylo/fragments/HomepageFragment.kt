package com.airstrike.stylo.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.stylo.R
import com.airstrike.stylo.adapters.ShoesAdapter
import com.airstrike.stylo.models.Shoe

class HomepageFragment : Fragment() {

    private lateinit var rvShoes: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val shoesMock = arrayListOf<Shoe>(
            Shoe("Nike", "Air Max 3", 111.12, true, listOf<String>("https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/6-4FCpXPVr.jpg",""),null),
            Shoe("Nike", "Air Max 3", 89.12, true, listOf<String>("https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/6-4FCpXPVr.jpg",""),null),
            Shoe("Addidas", "Air Max 3", 111.12, true, listOf<String>("https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/6-4FCpXPVr.jpg",""),null),
            Shoe("Nike", "Air Max 3", 45.12, true, listOf<String>("https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/6-4FCpXPVr.jpg",""),null),
            Shoe("Rebook", "Air Max 3", 85.12, true, listOf<String>("https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/HQ4324_4_900_900px-OlqkB8CQ.jpg",""),null),
            Shoe("Converste", "One Star3", 111.12, true, listOf<String>("https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/6-4FCpXPVr.jpg",""),null)
        )
        displayShoesInGrid(shoesMock)
    }
    private fun displayShoesInGrid(shoes : ArrayList<Shoe>)
    {
        rvShoes = requireView().findViewById(R.id.rv_shoes)

        val shoesAdapter = ShoesAdapter(shoes)

        rvShoes.layoutManager = GridLayoutManager(requireContext(),2)
        rvShoes.adapter = shoesAdapter
    }

}