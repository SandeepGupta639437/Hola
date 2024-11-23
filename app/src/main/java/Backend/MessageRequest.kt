package Backend

data class MessageRequest(
    val chatId: String,
    val message: String,
    val timestamp: Long
)