package Backend

data class Request(
    val description: String,
    val header: List<Any>,
    val method: String
)