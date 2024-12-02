package com.example.hola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Accounts : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_accounts, container, false)

        recyclerView = view.findViewById(R.id.accountrecyclerViewCommunity)

        val communities = listOf(
            AccountsData(R.drawable.image3, "Tech Enthusiasts"),
            AccountsData(R.drawable.image3, "Gaming Gurus"),
            AccountsData(R.drawable.image3, "Art Lovers"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),
            AccountsData(R.drawable.image3, "Bookworms"),

            )

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = AccountAdapter(communities)

        return view
    }
}
