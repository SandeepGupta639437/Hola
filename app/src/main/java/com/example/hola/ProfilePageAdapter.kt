package com.example.hola

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//class ProfilePageAdapter(private val context: Context, private val productArrayList: Any) :
//    RecyclerView.Adapter<ProfilePageAdapter.MyViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(context).inflate(R.layout.eachuserpost, parent, false)
//        return MyViewHolder(itemView)
//    }
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val currentItem = productArrayList[position]
////        Picasso.get().load(currentItem.thumbnail).into(holder.Image)
//    }
//    override fun getItemCount(): Int {
//        return productArrayList.size
//    }
//    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        var Image: ImageView = itemView.findViewById(R.id.userpost)
//        var follower_count : TextView =itemView.findViewById(R.id.followercount)
//        var following_count : TextView =itemView.findViewById(R.id.followingrcount)
//        var post_count : TextView =itemView.findViewById(R.id.postcount)
//    }
//}
