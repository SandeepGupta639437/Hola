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
import com.example.hola.UsePostAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class profilepage : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var usepostAdapter: UsePostAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profilepage, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewpost)
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
                    usepostAdapter = UsePostAdapter(requireContext(), productList)
                    recyclerView.adapter = usepostAdapter
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                } ?: Log.d("Home", "Response body is null")
            }
            override fun onFailure(call: Call<ApiData?>, t: Throwable) {
                Log.d("Home", "onFailure: ${t.message}")
            }
        }
        )
        return view
    }
}