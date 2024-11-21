package com.example.hola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hola.R

class topAdapter(private val images: List<ImageUnsplash>) :
    RecyclerView.Adapter<topAdapter.ImageViewHolder>() {

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val TopimageView: ImageView = itemView.findViewById<ImageView>(R.id.topimageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        Glide.with(holder.TopimageView.context)
            .load(image.urls.small)
            .into(holder.TopimageView)
    }

    override fun getItemCount() = images.size
}
