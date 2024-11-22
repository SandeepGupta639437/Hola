package com.example.hola

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CommunityAdapter(private val communityList: List<Community>) :
    RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val personImageView: ImageView = itemView.findViewById(R.id.imageViewCommunity)
        val textView: TextView = itemView.findViewById(R.id.textViewCommunityName)
        val Followers: TextView = itemView.findViewById(R.id.FollowersCount)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.community_item, parent, false)
        return CommunityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        val community = communityList[position]
        holder.personImageView.setImageResource(community.imageResId)
        holder.textView.text = community.name
        holder.Followers.text = community.followers

    }

    override fun getItemCount(): Int = communityList.size
}
