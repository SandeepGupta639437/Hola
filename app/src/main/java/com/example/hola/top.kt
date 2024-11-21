package com.example.hola

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class top : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val unsplashApiKey = "Be6lTicT7l_aHz1NQ_XqvAGHT6rj6KT2TLMQi1KE748"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 2) // 2 columns
        fetchImages()
        return view
    }

    private fun fetchImages() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val images = RetrofitInstance.api.getTrendingImages(unsplashApiKey)
                withContext(Dispatchers.Main) {
                    recyclerView.adapter = TrendingAdapter(images)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
