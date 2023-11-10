package pl.szymonleyk.betgame.register

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AccountRequest(
    @field:Length(min = 5, max=25)
    @field:NotBlank
    val username: String,

    @field:Length(max=50)
    @field:NotBlank
    val name: String,

    @field:Length(max=50)
    @field:NotBlank
    val surname: String
)
