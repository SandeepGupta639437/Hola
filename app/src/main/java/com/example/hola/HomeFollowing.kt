package com.example.hola

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
import com.example.hola.CommentPage
import com.example.hola.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFollowing : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var homeFollowingAdapter: HomeFollowingAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val imageview=view?.findViewById<ImageView>(R.id.commentic)
        imageview?.setOnClickListener{
            val intent= Intent(requireContext(), CommentPage::class.java)
            startActivity(intent)
        }
        val view = inflater.inflate(R.layout.fragment_home_following, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getApiData()
        retrofitData.enqueue(object : Callback<ApiData?> {
            override fun onResponse(call: Call<ApiData?>, response: Response<ApiData?>) {
                // Check if the response is successful and the body is not null
                response.body()?.let { responseBody ->
                    val productList = responseBody.products

                    homeFollowingAdapter = HomeFollowingAdapter(requireContext(), productList)
                    recyclerView.adapter = homeFollowingAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                } ?: Log.d("HomeFollowing", "Response body is null")
            }
            override fun onFailure(call: Call<ApiData?>, t: Throwable) {
                Log.d("HomeFollowing", "onFailure: ${t.message}")
            }
        }
        )
        return view // Ensure only one view is returned
    }
}