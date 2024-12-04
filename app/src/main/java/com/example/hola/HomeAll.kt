package com.example.hola

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeAll : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var homeAllAdapter: HomeAllAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("access_token")?:""

        val imageview=view?.findViewById<ImageView>(R.id.commentic)
        imageview?.setOnClickListener{
            val intent= Intent(requireContext(), CommentsPage::class.java)
            startActivity(intent)
        }
        val view = inflater.inflate(R.layout.fragment_home_all, container, false)
        recyclerView = view.findViewById(R.id.recyclerView2)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getHomeAll()
        retrofitData.enqueue(object : Callback<HomePageApi?> {
            override fun onResponse(call: Call<HomePageApi?>, response: Response<HomePageApi?>) {
                // Check if the response is successful and the body is not null
                response.body()?.let { responseBody ->
                    val productList = responseBody.posts


                    homeAllAdapter = HomeAllAdapter(requireContext(), productList)
                    recyclerView.adapter = homeAllAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                } ?: Log.d("HomeFollowing", "Response body is null")
            }
            override fun onFailure(call: Call<HomePageApi?>, t: Throwable) {
                Log.d("HomeFollowing", "onFailure: ${t.message}")
            }
        }
        )
        return view //
    }
}