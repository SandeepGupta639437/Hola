package com.example.hola

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.motion.widget.MotionScene.Transition.TransitionOnClick
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso // Ensure this import is present

class HomeAllAdapter(private val context: Context, private val productArrayList: List<Product>) :
    RecyclerView.Adapter<HomeAllAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachitemall, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productArrayList[position]
        holder.title.text = currentItem.title
        // Load the image using Picasso
        Picasso.get().load(currentItem.thumbnail).into(holder.image)
        Picasso.get().load(currentItem.thumbnail).into(holder.Image)
    }
    override fun getItemCount(): Int {
        return productArrayList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.productName)
        var image: ShapeableImageView = itemView.findViewById(R.id.productImage)
        var Image: ImageView = itemView.findViewById(R.id.productimage)
    }
}
