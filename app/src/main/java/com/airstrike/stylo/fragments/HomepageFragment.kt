package com.airstrike.stylo.fragments

import HomepageContent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airstrike.core.authentification.network.ResponseListener
import com.airstrike.core.authentification.network.models.ErrorResponseBody
import com.airstrike.stylo.R
import com.airstrike.stylo.adapters.ShoesAdapter
import com.airstrike.stylo.models.Shoe
import com.airstrike.web_services.models.responses.ProductResponse
import com.airstrike.web_services.network.request_handler.HomepageRequestHandler
import com.airstrike.web_services.network.request_handler.ProductsRequestHandler
import com.squareup.picasso.Picasso

class HomepageFragment : Fragment() {

    private lateinit var rvShoes: RecyclerView
    private lateinit var btn_man : Button
    private lateinit var btn_woman : Button
    private lateinit var btn_children : Button
    private lateinit var genderFilters : List<Button>
    private lateinit var heroImage : ImageView
    enum class Genders{
        muškarci,
        žene,
        djeca,
    }
    var activeGenderFilter = Genders.muškarci
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
        heroImage = view.findViewById(R.id.heroImage)
        btn_man = view.findViewById(R.id.tv_man)
        btn_woman = view.findViewById(R.id.tv_woman)
        btn_children= view.findViewById(R.id.tv_children)
        genderFilters = listOf(btn_man,btn_woman,btn_children)
        btn_man.setBackgroundColor(resources.getColor(R.color.gray_border))
        getHeroImageFromBackend()
        getProductsFromBackend()
        genderFilters.forEach {
            it.setOnClickListener {button->
                button.setBackgroundColor(resources.getColor(R.color.gray_border))
                genderFilters.forEach { x->
                    if(x != button) {
                        x.setBackgroundColor(resources.getColor(R.color.white))
                    }
                }
                activeGenderFilter = Genders.entries.get(genderFilters.indexOf(button))
                getProductsFromBackend()

            }
        }
    }
    private fun displayShoesInGrid(shoes : ArrayList<Shoe>)
    {
        rvShoes = requireView().findViewById(R.id.rv_shoes)

        val shoesAdapter = ShoesAdapter(shoes)

        rvShoes.layoutManager = GridLayoutManager(requireContext(),2)
        rvShoes.adapter = shoesAdapter
    }

    private fun getProductsFromBackend()
    {
        var productsRequestHandler = ProductsRequestHandler(activeGenderFilter.toString())
        productsRequestHandler.sendRequest(object : ResponseListener<List<ProductResponse>> {
            override fun onSuccess(response: List<ProductResponse>) {
                var products = mutableListOf<Shoe>()
                response.forEach {product ->
                    products.add(
                        Shoe(
                        product.manufacturer,
                        product.model.toString(),
                        product.price,
                        product.available,
                        product.extractImages(),
                        null
                    ))
                }
                displayShoesInGrid(products as ArrayList<Shoe>)
            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(requireContext(),R.string.error_products,Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(requireContext(),t.message,Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getHeroImageFromBackend()
    {
        var homepageRequestHandler = HomepageRequestHandler()
        homepageRequestHandler.sendRequest(object : ResponseListener<HomepageContent> {
            override fun onSuccess(response: HomepageContent) {
                //Picasso.with(heroImage.context).load(response.images[0].heroImg.url).into(heroImage)
               var url = response.images[0].heroImg.url
                var fullUrl = ""
                if(!(url.startsWith("http://") || url.startsWith("https://"))){
                    fullUrl = "https://" + url
                }
                Picasso.with(heroImage.context)
                    .load(fullUrl)
                    .into(heroImage, object : com.squareup.picasso.Callback {
                        override fun onSuccess() {
                            Log.i("PicassoHero","Success");
                        }

                        override fun onError() {
                            Log.i("PicassoErrorHerro","Couldnt load pic from url");
                            heroImage.setImageResource(R.drawable.noimage)
                        }


                    })

            }

            override fun onErrorResponse(response: ErrorResponseBody) {
                response.error?.let { error ->
                    Toast.makeText(requireContext(),"Error getting hero image",Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(t: Throwable) {
                Toast.makeText(requireContext(),t.message,Toast.LENGTH_LONG).show()
            }

        })
    }


}