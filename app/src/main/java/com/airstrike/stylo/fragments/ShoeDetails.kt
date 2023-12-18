package com.airstrike.stylo.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.airstrike.stylo.R
import com.airstrike.stylo.adapters.ImagePagerAdapter


class ShoeDetails : Fragment() {

    private lateinit var imageViewPager: ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shoe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imageViewPager: ViewPager2 = view.findViewById(R.id.picturesViewPager)
        val imageURls = arrayListOf<String>(
            "https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/6-4FCpXPVr.jpg"
            ,"https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/HQ4324_4_900_900px-OlqkB8CQ.jpg"
            ,"https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/HQ4324_1_900_900px-wi_vtOCF.jpg"
            ,"https://4611f7eebf8e380ba0cf-39d37efa03734d3c9cf6bef1463deb23.ssl.cf3.rackcdn.com/HQ4324_3_900_900px-fDX_pfa-.jpg")
        val adapter = ImagePagerAdapter(requireContext(), imageURls)
        imageViewPager.adapter = adapter
    }
    private fun loadColorPicker(colors : ArrayList<Color>)
    {
        TODO("Implement and assign adapter")
    }

}