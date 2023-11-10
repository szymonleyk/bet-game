package pl.szymonleyk.betgame.placebet.bet

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.placebet.PlaceBetRequest

@Service
class BetService(private val betRepository: BetRepository) {

    fun placeBet(placeBetRequest: PlaceBetRequest) {

    }

}