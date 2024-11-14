package Backend

import com.google.gson.annotations.SerializedName

data class Info(
    @SerializedName("_exporter_id") val exporterId: String,
    @SerializedName("_postman_id") val postmanId: String,
    val name: String,
    val schema: String
)
