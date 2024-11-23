package Backend

data class FollowRequest(
    val user_id: Int // Use `String` if the API explicitly requires the ID as a string
)