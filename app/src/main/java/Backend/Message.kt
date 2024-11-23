package Backend

data class Message(
    val id: String,
    val chatId: String,
    val message: String,
    val sender: String,
    val timestamp: Long
)