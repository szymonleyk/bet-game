package pl.szymonleyk.betgame.placebet.bet

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import pl.szymonleyk.betgame.topplayers.TopPlayer
import reactor.core.publisher.Flux

interface BetRepository : ReactiveCrudRepository<Bet, Int> {
    fun findAllByAccountId(accountId: Int): Flux<Bet>

    @Query("SELECT a.username as username, COUNT(CASE WHEN b.win = TRUE THEN 1 ELSE NULL END) as total_wins " +
            "FROM bet b " +
            "JOIN account a ON b.account_id = a.id " +
            "GROUP BY a.username " +
            "ORDER BY total_wins DESC")
    fun fetchTopPlayers(): Flux<TopPlayer>
}