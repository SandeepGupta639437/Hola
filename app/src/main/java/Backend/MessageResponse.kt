package Backend

data class MessageResponse(
    val id: String,
    val chatId: String,
    val message: String,
    val sender: String,
    val timestamp: Long
)