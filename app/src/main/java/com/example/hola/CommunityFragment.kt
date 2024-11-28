package com.example.hola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CommunityFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_community, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewCommunity)

        val communities = listOf(
            Community(R.drawable.image2, "Tech Enthusiasts","24M Followers"),
            Community(R.drawable.image2, "Gaming Gurus","2M Followers"),
            Community(R.drawable.image2, "Art Lovers","240M Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),
            Community(R.drawable.image2, "Bookworms","240K Followers"),

        )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = CommunityAdapter(communities)

        return view
    }
}
