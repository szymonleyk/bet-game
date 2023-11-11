package pl.szymonleyk.betgame.placebet.bet

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface BetRepository : ReactiveCrudRepository<Bet, Int> {
    fun findAllByAccountId(accountId: Int): Flux<Bet>
}