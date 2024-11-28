package com.example.hola

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class HomeAllAdapter(
    private val context: Context,
    private val productArrayList: List<Post>
) : RecyclerView.Adapter<HomeAllAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachitemall, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productArrayList[position]
//        holder.title.text = currentItem.title
//        holder.description.text = currentItem.description
        // Example: Load image using Picasso or Glide
        // Glide.with(context).load(currentItem.thumbnail).into(holder.image)
    }

    override fun getItemCount(): Int {
        return productArrayList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.textView)
        val description: TextView = itemView.findViewById(R.id.productName)
        val image: ShapeableImageView = itemView.findViewById(R.id.productImage)
    }
}
