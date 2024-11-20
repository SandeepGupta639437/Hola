package Backend

data class SignUpRequest(
    val full_name: String,
    val email: String,
    val password: String
)