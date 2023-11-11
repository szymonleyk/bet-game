package pl.szymonleyk.betgame.placebet

data class PlaceBetResponse(
    val betValue: Int,
    val betNumber: Int,
    val win: Boolean
)
