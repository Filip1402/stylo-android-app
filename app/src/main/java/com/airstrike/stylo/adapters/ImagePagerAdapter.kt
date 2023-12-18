package com.airstrike.stylo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.airstrike.stylo.R
import com.airstrike.stylo.models.Shoe
import com.squareup.picasso.Picasso
class ImagePagerAdapter(private val imageUrlList: List<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(private val itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private  val imageView : ImageView
        init {
            imageView = itemView.findViewById(R.id.imagePagerItem)
        }

        fun bind(url: String) {

            Picasso.with(imageView.context)
                .load(url)
                .into(imageView, object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        Log.i("Picasso","Success");
                    }

                    override fun onError() {
                        Log.i("PicassoError","Couldnt load pic from url");
                        imageView.setImageResource(R.drawable.noimage)
                    }
                })

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imagesView = LayoutInflater.from(parent.context).inflate(R.layout.image_pager_item_layout,parent,false)
        return ImageViewHolder(imagesView)
    }

    override fun getItemCount(): Int {
        return imageUrlList.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageUrlList[position])
    }


}
