package pl.szymonleyk.betgame.placebet.bet

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BetRepository : ReactiveCrudRepository<Bet, Int>