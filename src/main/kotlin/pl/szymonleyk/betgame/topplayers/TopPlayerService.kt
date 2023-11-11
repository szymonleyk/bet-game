package pl.szymonleyk.betgame.topplayers

import org.springframework.stereotype.Service
import pl.szymonleyk.betgame.placebet.bet.BetRepository
import reactor.core.publisher.Flux

@Service
class TopPlayerService(val betRepository: BetRepository) {
    fun fetchTopPlayers(): Flux<TopPlayer> = betRepository.fetchTopPlayers()
}