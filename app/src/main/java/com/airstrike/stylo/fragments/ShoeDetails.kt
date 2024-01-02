package com.airstrike.stylo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import com.airstrike.stylo.helpers.SecurePreferencesManager
import com.airstrike.stylo.listeners.ProductSelectionListener
import com.airstrike.stylo.models.Color
import com.airstrike.stylo.models.Shoe
import com.airstrike.stylo.models.CartItem
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
    private lateinit var addToCart_btn : Button

    private var colors = arrayListOf<com.airstrike.stylo.models.Color>()

    private  var selectedColor : Color? = null
    private  var selectedSize : ShoeSize? = null
    private  var selectedShoe : Shoe? = null
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
        addToCart_btn = view.findViewById(R.id.shoe_details_add_to_cart_btn)
        handleAddToCartBtn()
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
        sizesRecyclerView.adapter = ShoeSizesAdapter(sizes,this)
    }
    private fun retrieveProductDataFromBackend(color : Color?)
    {
        var productDetailsRequestHandler = ProductsDetailsHandler(productId)
        productDetailsRequestHandler.sendRequest(object : ResponseListener<ProductDetailsResponse> {
            override fun onSuccess(response: ProductDetailsResponse) {
                var variantIndex = color?.let { colors.indexOf(it) } ?: 0

                if (colors.isEmpty()) {
                    colors.addAll(response.variants.map { Color(it.color) })
                    loadColorPicker(colors)
                    loadBasicProductInfo(response,colors)
                }
                selectedColor = colors[variantIndex]
                var sizes = response.variants.getOrNull(variantIndex)?.sizes?.map {
                    ShoeSize(it.size, it.quantity)
                } as ArrayList<ShoeSize> ?: arrayListOf<ShoeSize>()

                loadSizesPicker(sizes)
                loadProductImages(response.variants.getOrNull(variantIndex)?.images as? ArrayList<String> ?: arrayListOf())
                selectedShoe = Shoe(
                    response.id,
                    response.manufacturer,
                    response.model,
                    response.price,
                    response.available,
                    response.variants[variantIndex].images,
                    response.variants[variantIndex].sku)
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(
                        requireContext(),
                        "Error retriving product details",
                        Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(requireContext(),t.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun handleAddToCartBtn() {
        addToCart_btn.setOnClickListener {
            val cartItem = createCartItemObject()
            if(cartItem == null) {
                Toast.makeText(context, R.string.color_size_not_selected, Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
            var cart = arrayListOf<CartItem>()
            val securePreferencesManager = SecurePreferencesManager(this.requireContext())
            val data = securePreferencesManager.getObjects("cart", CartItem::class.java)

            if (data != null) {
                cart.addAll(data)
            }
            cart = addItemToCart(cart,cartItem)
            securePreferencesManager.saveObject("cart",cart)
            Toast.makeText(context,R.string.product_added_to_cart,Toast.LENGTH_LONG).show()

        }
    }

    private fun addItemToCart(cart: ArrayList<CartItem>, cartItem: CartItem) : ArrayList<CartItem>{
         cart.forEach {
            if (it.shoe.Id == cartItem.shoe.Id && it.color.Name == cartItem.color.Name && it.shoeSize.size == cartItem.shoeSize.size) {
                it.quantity++
                return cart
            }
        }
        cart.add(cartItem)
        return cart
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

    private fun createCartItemObject() : CartItem?
    {
        if(selectedShoe == null || selectedColor == null ||selectedSize == null)
            return null
        else
            return CartItem(selectedShoe!!,selectedSize!!,selectedColor!!,1)
    }
    override fun openProductDetail(shoe: Shoe) {
        TODO("Not yet implemented")
    }
    override fun getProductVariantByColor(variantColor : Color)
    {
        selectedColor = variantColor
        retrieveProductDataFromBackend(variantColor)
    }
    override fun getProductVariantSize(size: ShoeSize) {
        selectedSize = size
    }
}