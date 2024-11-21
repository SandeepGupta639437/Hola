import com.example.hola.ImageUnsplash
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApiService {
    @GET("photos")
    suspend fun getTrendingImages(
        @Query("client_id") clientId: String, // Replace with your Unsplash API key
        @Query("per_page") perPage: Int = 20
    ): List<ImageUnsplash>
}

object RetrofitInstance {
    private const val BASE_URL = "https://hola-project.onrender.com"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }



    private const val BASE1_URL = "https://api.unsplash.com/"

    val api: UnsplashApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE1_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UnsplashApiService::class.java)
    }
}
