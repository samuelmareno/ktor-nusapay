package feature_auth.routes.dro


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDro(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)