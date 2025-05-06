package payment2go.co.id.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<T>(
    @SerialName("message")
    val message: String,
    @SerialName("response_code")
    val responseCode: Int,
    @SerialName("success")
    val success: Boolean,
    @SerialName("data")
    val data: T
)
