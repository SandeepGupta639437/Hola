package com.example.api

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hola.Product
import com.example.hola.R
import com.squareup.picasso.Picasso

class CommentAdapter(val context: Context, private val productArrayList: List<Product>) :
    RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.eachcomment, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = productArrayList[position]
        holder.title.text = currentItem.title
        // Load the image using Picasso
        Picasso.get().load(currentItem.thumbnail).into(holder.image)
    }
    override fun getItemCount(): Int {
        return productArrayList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.comment)
        var image:ImageView = itemView.findViewById(R.id.profileview)
        init {
            title=itemView.findViewById(R.id.comment)
            image=itemView.findViewById(R.id.profileview)

        }
    }
}