package com.example.hola

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.api.CommentAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommentPage : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var CommentAdapter: CommentAdapter
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_page)
        val backbutton = findViewById<ImageView>(R.id.backfromcomment)
        backbutton.setOnClickListener{
            val intent =Intent(this@CommentPage,MainActivity()::class.java)
            startActivity(intent)
        }
        recyclerView = findViewById(R.id.recyclerCommentView)

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retrofitBuilder.getApiData()
        retrofitData.enqueue( object : Callback<ApiData?> {
            override fun onResponse(call: Call<ApiData?>, response: Response<ApiData?>) {
                // Check if the response is successful and the body is not null
                val responseBody = response.body()
                val productList = responseBody?.products!!

                CommentAdapter= CommentAdapter(this@CommentPage,productList)
                recyclerView.adapter=CommentAdapter
                recyclerView.layoutManager=LinearLayoutManager(this@CommentPage)
            }
            override fun onFailure(call: Call<ApiData?>, t: Throwable) {
                Log.d("Home", "onFailure: ${t.message}")
            }
        }
        )
    }
}