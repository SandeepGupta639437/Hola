package com.example.hola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TagsFragment: Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tags, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewCommunity)

        val communities = listOf(
            tagdata(R.drawable.hashtag, "Tech Enthusiasts","24M Posts"),
            tagdata(R.drawable.hashtag, "Gaming Gurus","2M Posts"),
            tagdata(R.drawable.hashtag, "Art Lovers","240M Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),
            tagdata(R.drawable.hashtag, "Bookworms","240K Posts"),

            )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = tagAdapter(communities)

        return view
    }
}
