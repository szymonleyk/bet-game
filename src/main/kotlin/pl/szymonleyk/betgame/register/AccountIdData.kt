package pl.szymonleyk.betgame.register

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountIdData(
    @JsonProperty("id") val id: Int
)
