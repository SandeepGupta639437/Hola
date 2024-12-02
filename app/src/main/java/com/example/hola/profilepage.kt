package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class profilepage : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var usepostAdapter: ProfilePageAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profilepage, container, false)
        val editProfileButton:Button = view.findViewById(R.id.editProfile)
        editProfileButton.setOnClickListener{
            val intent =Intent(requireContext(),EditProfile::class.java)
            startActivity(intent)
        }
        val settingButton: ImageView = view.findViewById(R.id.settingfromprofile)
        settingButton.setOnClickListener{
            val intent =Intent(requireContext(),Settings::class.java)
            startActivity(intent)
        }
        val shareProfileButton:Button = view.findViewById(R.id.shareProfile)
        shareProfileButton.setOnClickListener{
            val intent =Intent(requireContext(),ShareProfile::class.java)
            startActivity(intent)
        }

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
                    usepostAdapter = ProfilePageAdapter(requireContext(), productList)
                    recyclerView.adapter = usepostAdapter
                    recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
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