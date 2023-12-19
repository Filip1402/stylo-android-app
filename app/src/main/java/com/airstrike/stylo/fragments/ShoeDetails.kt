package com.airstrike.stylo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.stylo.R
import com.airstrike.stylo.adapters.ImagePagerAdapter
import com.airstrike.stylo.adapters.ShoeColorsAdapter
import com.airstrike.stylo.adapters.ShoeSizesAdapter
import com.airstrike.stylo.listeners.ProductSelectionListener
import com.airstrike.stylo.models.Color
import com.airstrike.stylo.models.Shoe
import com.airstrike.stylo.models.ShoeSize
import com.airstrike.web_services.models.responses.ProductDetailsResponse
import com.airstrike.web_services.network.request_handler.ProductsDetailsHandler


class ShoeDetails(private val productId : String) : Fragment(), ProductSelectionListener {

    private lateinit var imageViewPager: ViewPager2
    private lateinit var colorsRecyclerView: RecyclerView
    private lateinit var sizesRecyclerView: RecyclerView
    private lateinit var brand_tv: TextView
    private lateinit var model_tv: TextView
    private lateinit var colors_tv: TextView
    private lateinit var categories_tv: TextView
    private lateinit var price_tv: TextView

    private var colors = arrayListOf<com.airstrike.stylo.models.Color>()

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

        brand_tv = view.findViewById(R.id.details_shoeBrand)
        model_tv = view.findViewById(R.id.details_shoeModel)
        categories_tv = view.findViewById(R.id.details_shoeCategory)
        colors_tv = view.findViewById(R.id.details_shoeColors)
        price_tv = view.findViewById(R.id.details_shoePrice)
        retrieveProductDataFromBackend(null)
    }
    private fun loadProductImages(imageUrls: ArrayList<String>) {
        imageViewPager = requireView().findViewById(R.id.picturesViewPager)
        val adapter = ImagePagerAdapter(imageUrls)
        imageViewPager.adapter = adapter
    }

    private fun loadColorPicker(colors : ArrayList<com.airstrike.stylo.models.Color>)
    {
        colorsRecyclerView =  requireView().findViewById(R.id.details_color_selector_layout)
        colorsRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        colorsRecyclerView.adapter = ShoeColorsAdapter(colors,this)
    }

    private fun loadSizesPicker(sizes : ArrayList<ShoeSize>) {
        sizes.removeIf { it.quantity <= 0 }
        sizes.sortBy { it.size }
        sizesRecyclerView = requireView().findViewById(R.id.details_size_selector_layout)
        sizesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        sizesRecyclerView.adapter = ShoeSizesAdapter(sizes)
    }
    private fun retrieveProductDataFromBackend(color : Color?)
    {
        var productDetailsRequestHandler = ProductsDetailsHandler(productId)
        productDetailsRequestHandler.sendRequest(object : ResponseListener<ProductDetailsResponse> {
            override fun onSuccess(response: ProductDetailsResponse) {
                var variantIndex = 0
                if(colors.size == 0)
                {
                    response.variants.forEach {
                        colors.add(com.airstrike.stylo.models.Color(it.color))
                    }
                }
                if (color != null)
                {
                    variantIndex = colors.indexOf(color)

                }
                else
                {
                    loadColorPicker(colors)
                    loadBasicProductInfo(response,colors)
                }

                var sizes = arrayListOf<ShoeSize>()
                response.variants[variantIndex].sizes.forEach {
                    sizes.add(ShoeSize(it.size,it.quantity))
                }
                loadSizesPicker(sizes)
                loadProductImages(response.variants[variantIndex].images as ArrayList<String>)
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(requireContext(),"Error retriving product details", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun loadBasicProductInfo(response: ProductDetailsResponse,colors : ArrayList<com.airstrike.stylo.models.Color>) {
        brand_tv.text = response.manufacturer
        model_tv.text = response.model
        var categories = ""
        response.categories.forEach {
            categories+= it+", "
        }
        categories_tv.text = categories
        var colorsString = ""
        colors.forEach{
            colorsString+=it.Name + ", "
        }
        colors_tv.text = colorsString
        price_tv.text = response.price.toString() + " EUR"
    }

    override fun openProductDetail(shoe: Shoe) {
        TODO("Not yet implemented")
    }

    override fun getProductVariantByColor(variantColor : Color)
    {
        retrieveProductDataFromBackend(variantColor)
    }

}