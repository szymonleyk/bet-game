package pl.szymonleyk.betgame.placebet

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class PlaceBetRequest(
    val betValue: Int,

    @field:Min(1)
    @field:Max(10)
    val betNumber: Int,

    val acocuntId: Int
)
