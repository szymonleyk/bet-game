package pl.szymonleyk.betgame.register

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length

data class AccountData(
    @field:Length(min = 5, max=25)
    @field:NotBlank
    var username: String,

    @field:Length(max=50)
    @field:NotBlank
    var name: String,

    @field:Length(max=50)
    @field:NotBlank
    var surname: String
)
