package pl.szymonleyk.betgame.topplayers

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import pl.szymonleyk.betgame.placebet.bet.Bet
import reactor.core.publisher.Flux

interface TopPlayerDataProjection: ReactiveCrudRepository<Bet, Int> {

}