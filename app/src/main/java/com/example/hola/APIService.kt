import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val access: String, val refresh: String)

interface ApiService {
    @POST("/api/auth/login/")
    fun login(@Body request: LoginRequest): Call<com.example.hola.LoginResponse>
}
