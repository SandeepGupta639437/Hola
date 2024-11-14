package Backend

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {


    @POST("api/auth/register/")
    suspend fun registerUser(@Body signup: SignUpRequest): Response<SignUpResponse>


}