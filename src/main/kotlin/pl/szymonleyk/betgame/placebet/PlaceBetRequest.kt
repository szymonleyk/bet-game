package pl.szymonleyk.betgame.placebet

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class PlaceBetRequest(
    @field:Min(1)
    val betValue: Int,

    @field:Min(1)
    @field:Max(10)
    val betNumber: Int,

    @field:Min(1)
    val accountId: Int
)
