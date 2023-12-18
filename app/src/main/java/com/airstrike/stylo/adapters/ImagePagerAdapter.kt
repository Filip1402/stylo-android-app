package com.airstrike.stylo.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.airstrike.stylo.R
import com.squareup.picasso.Picasso

class ImagePagerAdapter(private val context: Context, private val imageUrls: List<String>) :
    RecyclerView.Adapter<ImagePagerAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.image_pager_item_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val url = imageUrls[position]
        val fullUrl = if (!(url.startsWith("http://") || url.startsWith("https://"))) {
            "https://$url"
        } else {
            url
        }

        Picasso.with(holder.imageView.context)
            .load(fullUrl)
            .into(holder.imageView, object : com.squareup.picasso.Callback {
                override fun onSuccess() {
                    // Success
                }

                override fun onError() {
                    holder.imageView.setImageResource(R.drawable.noimage)
                }
            })
    }

    override fun getItemCount(): Int {
        return imageUrls.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imagePagerItem)
    }
}