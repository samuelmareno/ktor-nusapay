package feature_auth.routes.dro


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("accessTokenExpiryTime")
    val accessTokenExpiryTime: String,
    @SerialName("additionalInfo")
    val additionalInfo: List<String>,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("refreshTokenExpiryTime")
    val refreshTokenExpiryTime: String,
    @SerialName("tokenType")
    val tokenType: String
)