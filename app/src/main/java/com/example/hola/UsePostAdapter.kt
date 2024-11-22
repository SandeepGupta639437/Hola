package com.example.hola

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso


class UsePostAdapter{}
//    (val context: Context, private val productArrayList: List<Product>) :{
//    RecyclerView.Adapter<UsePostAdapter.MyViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(context).inflate(R.layout.eachcomments, parent, false)
//        return MyViewHolder(itemView)
//    }
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentItem = productArrayList[position]
//        Picasso.get().load(currentItem.thumbnail).into(holder.image)
//    }
//    override fun getItemCount(): Int {
//        return productArrayList.size
//    }
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var image: ImageView = itemView.findViewById(R.id.postview)
//        init {
//            image=itemView.findViewById(R.id.postview)
//
//        }
//    }
//}